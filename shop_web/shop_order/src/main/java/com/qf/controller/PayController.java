package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.qf.entity.Order;
import com.qf.service.IOrderService;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by  .Life on 2019/07/27/0027 15:13
 */
@Controller
@RequestMapping("/pay")
public class PayController {
    @Reference
    private IOrderService orderService;

    /*
    * 调用支付宝接口支付
    */
    @RequestMapping("/alipay")
    public void aliPay(String orderid,HttpServletResponse response){
        //查询订单总金额
        Order order = orderService.queryByOid(orderid);



        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                "2016100900648325",
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCImzow4GCWSSJXtetaQj1cSJvjsVDzAFeVCVBQ9at6YDGhzoWaEmpMVyHPiImmFgrZbmDMiK6G5cSbotrETMgzsrGjxio6RuLmHy0adVR5iE4UuGb3PeZoMKqj4Snwee/DjeI+HFHN4ntbFWoeTb9KAnz4GPvWFxp2RMUN5W9JueSptYeXzs6I+VabfKug8JQZtShJLMRd0BF2SHOrp+1z5tOWzPpqlAjLNMhzACuwOp5+51l6yok9D/MloMS55vLWYEW8oAy1IAcjLmbMStYpyTIV/Wlm6hpQhh7HKFUjBYIJHL4ssOsspgXw+qe9gDAwYvQOejW1v/L3YDvF4m17AgMBAAECggEAPKXt59U2DILbpGU34BKYe2ghDa2jar80Iw7HgaoQP3yFjHjMB+M1Hj9w21qbAgm3N2ExyJzGH03TQ+XBinUsTQeApl1MUiM2iUQ+GgkNQAer4rcbv5jTul4g5DELFaCNOhy73SdliiqK6Z1QR7PswUbphJxS8nG/ZdKSSeEaz3f5PmgMHVZlM9DYw508juDnvWUohyO6QlliBVYvBMVyv7MCN+ARayB2sjoqqHnBSdScPAj82pN63cBCWaiQkgAnIOrD29kAcDvsjYLzYlHl8Mf6jJp3w93gyAhemCKoETJN9g1iVMipni79yFKKw9VCl8vA/0BS5XsI91V8Ix/vwQKBgQDSfY8d+n2KorILDJaRI0TumnUb9rWGbLY08Jxd8laj07uMO/Iwi8CMx/7FTqy5WrNkhCVFhsueqpE6vrmLkSG/z6V3O8u4/0gmAl8ZZJEvUE5RF8Qkpgr47Y3V6jrzEZ/h/IJVIuj5VX7p/iNQUbt9mI7gLyaLA71pvgCxQ0aVlQKBgQCmJEFZTr4h9AVxPyrM4zEdGcdH+Rs7osxBeCftyb+654/YihCNqSyURcpGsNnxJCJyxtYhb6n7Gj7Osq9i4GfI8VCwvdAQ5sty9XSQlDvv42xHgfH/b6o6docK/N9NwyzmbwvNLkkwxvVPaHTFhYevc5fjoV/BKxhMq3w6i18SzwKBgHZVuFv2MxX5mXa0wQEffuVVWHk8HZlRDK5+hqoaT7MSmeGg4euA5eM/nG94Z4pc/3WHBR0tRtQQuEz6ZH2ePgcDydZae5GpkvorYyN3clfTIfRdFBw2bPy9MHwOjs2QHv6BYxRR6AAknJEYGupQyMM3PTuzxkto8te7xc/iLpBxAoGAAQ/sRwjk2Ey6GC5CNMsjDj+S8ZfbH7d7vNNq1e9bRXOjjMvdLTquqUgE8gzBZ/RGMBurVM1k9dEGI8YqruQmZbd9P5QHF4SSOmrtUttPwsnecFqZVHu/R95oIJ7bQqED0XlWRvqLxz8OZHKThIvFAU8o4nCurwp6fkr+YVVwxysCgYEArVstnNCVSElX5H6iGfMBSlUNxe3utkk9B8TBubLJAQW9hS98ExnZJvE2p9VbUcd++5s5taBGxhcY8ibOilquzU5bg4NjKdKz0l4+YynK0ndLi8JAkKky5wJg9u88QynbC6LD3pvQkIa3fnP7pl2x3hMk1fPOz8XCSATDH7cChCw=",//开发者私钥
                "json",
                "utf-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAugohtYaFnHjKEJ4qaOefC1OZUm3jr9woMZc5eII4+K+yF2VeYTNiusoJvo/5pLVVZBNuah6rMNwJIOTLukKeQ9Wxydk40zs4uNb9JepfI+DaX7Kpv7HcEefnPgNzdD8LCqUel9zrQT/QmmFt0wGbDgUSbv+hKtTb6BURNR1lMRV5g1zQgpQVtlg4fslOq3zkH60i9+Op4sfEIMzVimTun13PYbg5K7lcaRlRgLaWzaMOLO1+oip8SA/rosBd8WEHTQt1Krn//i7U1W4mnlDiCCJOYlPDwAjA/paswWwpVBHut4fIdPfXo3tJYgP/G8iyXoHkPMRd5bIKuhVVxNnBTQIDAQAB",//阿里的公钥
                "RSA2"); //获得初始化的AlipayClient
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        //设置同步返回的url--用户支付完成跳到哪里
        alipayRequest.setReturnUrl("http://localhost:8081");
        //设置异步返回的url--用户支付的结果通知的url
        alipayRequest.setNotifyUrl("http://localhost:8086/pay/payCompent");//在公共参数中设置回跳和通知地址
        //支付请求体
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\""+orderid+"\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":"+order.getAllprice().doubleValue()+"," +
                "    \"subject\":\""+orderid+"\"," +
                "    \"body\":\""+orderid+"\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");//填充业务参数

        //生成支付的表单页面
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");
        try {
            //直接将完整的支付表单html输出到用户的页面
            response.getWriter().write(form);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/payCompent")
    @ResponseBody
    public void payCompent(String out_trade_no,String trade_status){

        if (trade_status.equals("TRADE_CLOSED")){
            //说明支付成功,修改订单状态
            orderService.updateOrderStatus(out_trade_no,1);
        }
    }
}
