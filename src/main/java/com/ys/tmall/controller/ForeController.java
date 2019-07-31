package com.ys.tmall.controller;

import com.ys.tmall.bean.*;
import com.ys.tmall.common.api.CommonResult;
import com.ys.tmall.comparator.*;
import com.ys.tmall.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yaosh
 * @data 2019/7/15
 */
@Api(description = "用于前台json数据管理")
@RestController
public class ForeController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    ProductService productService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderService orderService;

    @ApiOperation(value = "首页分类数据查询", notes = "用于首页分类展示")
    @GetMapping("/forehome")
    public List<Category> listAll(){
        return categoryService.listAll();
    }

    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping("/foreregister")
    public CommonResult redister(@RequestBody User user){
        boolean b = userService.checkUserName(user);
        if(b){
            userService.add(user);
            return CommonResult.success(user);
        }else {
            String message = "用户名已被占用,hh";
            return CommonResult.failed(message);
        }
    }

    @ApiOperation(value = "用户登入", notes = "用户登入")
    @PostMapping("/forelogin")
    public CommonResult login(@RequestBody User user, HttpSession session){
        boolean b = userService.checkUserNameAndPwd(user);
        if(!b){
            session.setAttribute("user",user);
            String message = "登入成功";
            return CommonResult.success("user",message);
        }else {
            String message = "用户名或密码错误";
            return CommonResult.failed(message);
        }
    }

    @ApiOperation(value = "产品管理", notes = "跟据产品的id查找，产品信息，对应的产品图片、对应的产品评价")
    @GetMapping("/foreproduct/{pid}")
    public CommonResult product(@PathVariable("pid") int pid){
        //产品
        Product product = productService.list(pid);

        //产品评论
        List<Review> reviews = reviewService.listByPid(pid);

        //产品值
        List<Propertyvalue> propertyvalue = propertyValueService.listByPid(pid);

        Map<String,Object> map= new HashMap<>();
        map.put("product", product);
        map.put("pvs", propertyvalue);
        map.put("reviews", reviews);

        return CommonResult.success(map);
    }

    @ApiOperation(value = "检查用户登入情况", notes = "检查用户登入情况")
    @GetMapping("/forecheckLogin")
    public CommonResult login(HttpSession session){

        User user = (User)session.getAttribute("user");
        if(user == null){
            return  CommonResult.failed("未登入");
        }else {
            return CommonResult.success(user);
        }

    }

    @ApiOperation(value = "首页分类数据查询", notes = "用于首页分类展示")
    @GetMapping("/forecategory/{cid}")
    public Category listAll(@PathVariable("cid")int cid, @RequestParam("sort")String sort){
        Category category = categoryService.list(cid);

        List<Product> products = category.getProducts();

        if(sort != null){
            switch (sort){
                case "all":
                    Collections.sort(products, new ProductAllComparator());
                    break;
                case "review":
                    Collections.sort(products, new ProductReviewComparator());
                    break;
                case "date":
                    Collections.sort(products, new ProductdateComparator());
                    break;
                case "saleCount":
                    Collections.sort(products, new ProductSaleComparator());
                    break;
                case "price":
                    Collections.sort(products, new ProductPriceComparator());
                    break;
            }
        }

        return category;
    }

    @ApiOperation(value = "搜索管理", notes = "跟据产品的关键之查找产品")
    @PostMapping("/foresearch")
    public List<Product> product(@RequestParam("keyword") String keyword){
        List<Product> products = productService.listForSearch(keyword);
        return products;
    }

//===================================== 下面时需要登入的情况 ================================================//

    @ApiOperation(value = "立即购买", notes = "返回订单项的id,用与生成订单.")
    @GetMapping("/forebuyone")
    public int buynow(@RequestParam("pid")int pid,
                      @RequestParam("num")int num,
                      HttpSession session){

        User user = (User) session.getAttribute("user");
        int userID = userService.listUserID(user);

        int orderitemId = 0;
        boolean found = false;
        List<Orderitem> orderitems = orderItemService.listAllByUid(userID);
        for(Orderitem orderitem : orderitems){
            if(orderitem.getPid() == pid){
                int newNum = orderitem.getNumber() + num;
                orderitem.setNumber(newNum);
                orderItemService.update(orderitem);
                orderitemId = orderitem.getId();
                found = true;
                break;
            }
        }
        if(!found){
            Orderitem orderitem = new Orderitem();
            orderitem.setPid(pid);
            orderitem.setNumber(num);
            orderitem.setUid(userID);
            orderItemService.add(orderitem);
            orderitemId = orderitem.getId();
        }

        return orderitemId;

    }

    @ApiOperation(value = "结算页面数据", notes = "跟据产品的关键之查找产品")
    @GetMapping("/forebuy")
    public CommonResult buy(String[] oiid, HttpSession session){

        List<Orderitem> orderitems = new ArrayList<>();
        float total = 0;

        for(String strid : oiid){
            int id = Integer.parseInt(strid);
            Orderitem orderitem = orderItemService.list(id);
            orderitems.add(orderitem);
            total += orderitem.getProduct().getPromoteprice() * orderitem.getNumber();
        }

        //
        session.setAttribute("orderitems", orderitems);

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("orderItems", orderitems);

        return CommonResult.success(map);
    }

    @ApiOperation(value = "加入购物车", notes = "如果购物车中存在商品则进行叠加，否则新建订单项")
    @GetMapping("/foreaddCart")
    public CommonResult addCart(@RequestParam("pid")int pid,
                                @RequestParam("num")int num,
                                HttpSession session){
        User user = (User) session.getAttribute("user");
        if(null==user){
            return CommonResult.failed("未登录");
        }
        int userID = userService.listUserID(user);

        boolean found = false;
        List<Orderitem> orderitems = orderItemService.listAllByUid(userID);
        for(Orderitem orderitem : orderitems){
            if(orderitem.getPid() == pid){
                int newNum = orderitem.getNumber() + num;
                orderitem.setNumber(newNum);
                orderItemService.update(orderitem);
                found = true;
                break;
            }
        }
        if(!found){
            Orderitem orderitem = new Orderitem();
            orderitem.setPid(pid);
            orderitem.setNumber(num);
            orderitem.setUid(userID);
            orderItemService.add(orderitem);
        }
        return CommonResult.success("这里没有携带数据");
    }

    @ApiOperation(value = "购物车页面数据", notes = "查询订单项数据")
    @GetMapping("/forecart")
    public CommonResult cart(HttpSession session){

        User user = (User) session.getAttribute("user");
        if(null==user){
            return CommonResult.failed("未登录");
        }
        int userID = userService.listUserID(user);
        List<Orderitem> orderitems = orderItemService.listAllByUid(userID);
        return CommonResult.success(orderitems);
    }

    @ApiOperation(value = "购物车页面订单数量修改", notes = "购物车页面订单数量修改")
    @GetMapping("/forechangeOrderItem")
    public CommonResult changeOrderItem(@RequestParam("pid")int pid,
                                        @RequestParam("num")int num,
                                        HttpSession session){

        User user = (User) session.getAttribute("user");
        if(null==user){
            return CommonResult.failed("未登录");
        }
        int userID = userService.listUserID(user);

        List<Orderitem> orderitems = orderItemService.listAllByUid(userID);
        for(Orderitem orderitem : orderitems){
            if(orderitem.getPid() == pid){

                orderitem.setNumber(num);
                orderItemService.update(orderitem);

                break;
            }
        }
        return CommonResult.success("这里没有携带数据");
    }

    @ApiOperation(value = "购物车页面订单删除", notes = "购物车页面订单删除")
    @GetMapping("/foredeleteOrderItem")
    public CommonResult changeOrderItem(@RequestParam("oiid")int oiid){

        orderItemService.delete(oiid);

        return CommonResult.success("这里没有携带数据");
    }

    @ApiOperation(value = "创建订单", notes = "根据购物车选择的订单项，创建订单")
    @PostMapping("/forecreateOrder")
    public CommonResult createOrder(@RequestBody Order order, HttpSession session){

        User user = (User) session.getAttribute("user");
        if(null==user){
            return CommonResult.failed("未登录");
        }
        int userID = userService.listUserID(user);
        order.setUid(userID);

        Date date = new Date();
        UUID uuid = UUID.randomUUID();
        String substring = uuid.toString().substring(0, 4);
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date) + substring;
        order.setOrdercode(orderCode);

        order.setCreatedate(date);

        order.setStatus("waitPay");

        orderService.add(order);

        float total = 0;
        List<Orderitem> orderitems = (List<Orderitem>) session.getAttribute("orderitems");
        for(Orderitem orderitem : orderitems){
            //更新订单项，添加oid
            orderitem.setOid(order.getId());
            orderItemService.update(orderitem);
            total += orderitem.getProduct().getPromoteprice() * orderitem.getNumber();
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("oid", order.getId());
        map.put("total", total);

        return CommonResult.success(map);
    }

    @ApiOperation(value = "支付完成", notes = "支付完成后，跳转所需数据")
    @GetMapping("/forepayed")
    public CommonResult payed(@RequestParam("oid")int oid){

        Order order = orderService.list(oid);

        order.setPaydate(new Date());
        order.setStatus("waitDelivery");
        orderService.update(order);

        return CommonResult.success(order);
    }

    @ApiOperation(value = "订单页面数据", notes = "订单页面数据")
    @GetMapping("/forebought")
    public CommonResult payed(HttpSession session){

        User user = (User) session.getAttribute("user");
        if(null==user){
            return CommonResult.failed("未登录");
        }
        int userID = userService.listUserID(user);

        List<Order> orders = orderService.listAllByUserID(userID);

        return CommonResult.success(orders);
    }


    @ApiOperation(value = "确认收货", notes = "确认收货")
    @GetMapping("/foreconfirmPay")
    public CommonResult confirmPay(@RequestParam("oid")int oid){

        Order order = orderService.list(oid);

        return CommonResult.success(order);
    }

    @ApiOperation(value = "确认收货完成", notes = "确认收货完成")
    @GetMapping("/foreorderConfirmed")
    public CommonResult Confirmed(@RequestParam("oid")int oid){

        Order order = orderService.list(oid);

        order.setConfirmdate(new Date());
        order.setStatus("waitReview");
        orderService.update(order);

        return CommonResult.success(order);
    }

    @ApiOperation(value = "我的订单删除", notes = "我的订单删除")
    @PutMapping("/foredeleteOrder")
    public CommonResult deleteOrder(@RequestParam("oid")int oid){

        Order order = orderService.list(oid);

        order.setStatus("delete");
        orderService.update(order);

        return CommonResult.success(order);
    }

    @ApiOperation(value = "评论页所需数据", notes = "评论页所需数据")
    @GetMapping("/forereview")
    public CommonResult review(@RequestParam("oid")int oid, HttpSession session){

        Order order = orderService.list(oid);

        User user = (User) session.getAttribute("user");
        if(null==user){
            return CommonResult.failed("未登录");
        }
        int userID = userService.listUserID(user);

        Product product = new Product();
        List<Orderitem> orderitems = orderItemService.listAllByUid(userID);
        for(Orderitem orderitem : orderitems){
            if(orderitem.getOid() == oid){
                product = orderitem.getProduct();
                break;
            }
        }

        Review review = new Review();
        review.setPid(product.getId());
        review.setUid(userID);

        HashMap<String, Object> map = new HashMap<>();
        map.put("o", order);
        map.put("p", product);
        map.put("review", review);

        return CommonResult.success(map);
    }

    @ApiOperation(value = "创建评论", notes = "创建评论")
    @PostMapping("/foredoreview")
    public CommonResult doreview(@RequestParam("oid") int oid,
                                 @RequestParam("pid") int pid,
                                 @RequestParam("content") String content,
                                 HttpSession session){
        //更新订单状态
        Order order = orderService.list(oid);
        order.setStatus("finish");
        orderService.update(order);

        User user = (User) session.getAttribute("user");
        if(null==user){
            return CommonResult.failed("未登录");
        }
        int userID = userService.listUserID(user);

        Review review = new Review();
        review.setPid(pid);
        review.setUid(userID);
        review.setContent(content);
        review.setCreatedate(new Date());

        reviewService.add(review);

        return CommonResult.success(review);
    }
}
