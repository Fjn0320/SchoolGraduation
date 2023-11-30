package com.example.demo.service;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.example.demo.common.Wrapper;
import com.example.demo.exception.ServiceException;
import com.example.demo.mapper.ShopMapper;
import com.example.demo.pojo.*;
import com.example.demo.until.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ShopService {
    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private CartService cartService;
    public List<ShopPojo> getPageAllShop(Integer pageNum, Integer pageSize, String username, String shopname) {
        return shopMapper.getPageAllShop(pageNum,pageSize,username,shopname);
    }

    public Integer selectShopNum(String username, String shopname) {
        return shopMapper.selectShopNum(username,shopname);
    }

    public ShopPojo insertShop(ShopPojo shopPojo) {
        shopMapper.insertShop(shopPojo);
        return shopPojo;
    }

    public ShopPojo updateShop(ShopPojo shopPojo) {
        shopMapper.updateShop(shopPojo);
        return  shopPojo;
    }

    public int deleteShop(Integer id) {
        return shopMapper.deleteShop(id);
    }

    public List<ShopInfoPojo> getShopInfo() {
        return shopMapper.getShopInfo();
    }

    public String selectById(Integer id) {
        return shopMapper.selectById(id);
    }

    public List<ShopPojo> getShopMenu(String username) {
        return shopMapper.getShopMenu(username);
    }

    public ShopInfoPojo getShopInfoId(Integer id) {
        return shopMapper.getShopInfoId(id);
    }

    public List<ShopSortPojo> getShopSort(String username) {
        return shopMapper.getShopSort(username);
    }

    public List<ShopPojo> getAllMenu(String shopsort, String username) {
        List<ShopPojo> allMenu = shopMapper.getAllMenu(shopsort, username);
        for (ShopPojo shopPojo :
                allMenu) {
            BigDecimal bigDecimal = shopPojo.getShopprice().multiply(BigDecimal.valueOf(0.7)).setScale(2, RoundingMode.UP);
            shopPojo.setShopmoey(bigDecimal);

        }
        return allMenu;
    }

    public ShopSortPojo getShopSortId(Integer id) {
        return shopMapper.getShopSortId(id);
    }

    public ShopPojo selectAllById(int shopid) {//根据商品id来查询商品信息
        ShopPojo shopPojo = shopMapper.selectAllById(shopid);
        BigDecimal bigDecimal = shopPojo.getShopprice().multiply(BigDecimal.valueOf(0.7)).setScale(2, RoundingMode.UP);
        shopPojo.setShopmoey(bigDecimal);
        return shopPojo;
    }


    /**
     * 订单
     * @param shopDingDanDTO
     * @return
     */
    public ShopDingDanDTO addDingDan(ShopDingDanDTO shopDingDanDTO) {
        Integer shopid = shopDingDanDTO.getShopid();//获取商家id
        //下单时获取token值看是否有用户登录,没有登录就不能下单
        UserPojo currentUser = TokenUtils.getCurrentUser();
        Integer userid = currentUser.getId();
        if (userid==null){
            throw  new ServiceException(999,"没登录");
        }
        //查询该用户是否有在该商家购物的没有的话不能下单
        List<ShopCartPojo> shopCartPojos = cartService.selectAllCart(userid, shopid);
                for (ShopCartPojo cart:shopCartPojos) {
            ShopPojo shopPojo =  this.selectAllById(cart.getShopid());
            cart.setShopPojo(shopPojo);
            //查询出商家的信息
            ShopInfoPojo shopInfoId = this.getShopInfoId(cart.getShopid());
            cart.setShopInfoPojo(shopInfoId);
        }
        if (CollUtil.isEmpty(shopCartPojos)){
            throw  new ServiceException(888,"无商品");
        }
        ShopDingDan shopDingDan = new ShopDingDan();
        shopDingDan.setShoping(shopid);//设置商家id
        shopDingDan.setGoname(shopDingDanDTO.getUser());//下单人
        shopDingDan.setGoadress(shopDingDanDTO.getAddress());//下单地址
        shopDingDan.setGophone(shopDingDanDTO.getPhone());//下单电话
        shopDingDan.setShopcomment(shopDingDanDTO.getComment());//下单备注

        //设置商品信息
        Amount calc = cartService.calc(userid, shopid);
        shopDingDan.setShopmoney(calc.getActual());

        //得到购物车的总数
        Integer sum = shopCartPojos.stream().map(ShopCartPojo::getNum).reduce(Integer::sum).orElse(0);
        shopDingDan.setShopname(shopCartPojos.get(0).getShopPojo().getShopname()+"等"+sum+"件商品");//设置订单名称
        //然后设置编号
        shopDingDan.setShopid(IdUtil.getSnowflakeNextIdStr());//雪花算法
        this.addShopDingDan(shopDingDan);

//        //添加到订单item中
        DingDanItem dingDanItem = new DingDanItem();
        dingDanItem.setShopname(shopCartPojos.get(0).getShopPojo().getShopname()+"等"+sum+"件商品");
        dingDanItem.setShopurl(shopCartPojos.get(0).getShopPojo().getShopurl());
        dingDanItem.setShopmoney(calc.getActual());
        dingDanItem.setShopid(shopid);
        dingDanItem.setDingdanid(shopDingDan.getShopid());//获取订单编号
        dingDanItem.setUserid(userid);//用户的id
        dingDanItem.setSum(sum);
        dingDanItem.setShoping(shopCartPojos.get(0).getShopPojo().getShopping());
        this.addDingDanItem(dingDanItem);

        //清除购物车
        cartService.deleteCart(userid,shopid);

        return shopDingDanDTO;
    }
    //将订单输入到数据库

    public void addShopDingDan(ShopDingDan shopDingDan) {//根据商品id来查询商品信息
       shopMapper.addShopDingDan(shopDingDan);
    }

    public void addDingDanItem(DingDanItem dingDanItem){
        shopMapper.addDingDanItem(dingDanItem);
    }

    public List<DingDanItem> getDingItem(Integer userid) {
        return shopMapper.getDingItem(userid);
    }

    public int addPingFen(Integer dingdanid, String comment) {
        //根据传过来的DingDanItem的id来查找后将赋值ShopPingFen
        System.out.println(dingdanid);
        System.out.println(comment);
        DingDanItem byId = this.getById(dingdanid);
        ShopPingFen shopPingFen = new ShopPingFen();
        shopPingFen.setComment(comment);
        int userid = byId.getUserid();
        UserPojo userPojo  = this.selectCartById(userid);
        shopPingFen.setGoname(userPojo.getUsername());
        shopPingFen.setShopname(byId.getShoping());
        shopPingFen.setShopid(userid);
        shopPingFen.setDingdanid(dingdanid);
        return shopMapper.addPingFen(shopPingFen);
    }
    //根据DingDanItem的id来查询
    public DingDanItem getById(int id){
        return shopMapper.getById(id);
    }

    public UserPojo selectCartById(int id){
        return shopMapper.selectCartById(id);
    }

}



















