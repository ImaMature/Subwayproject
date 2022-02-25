package abc.controller;


import abc.service.subwayService;
import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class subController {

    @Autowired
    subwayService sservice;
    // 메인페이지 매핑[ 연결 ]
    @GetMapping("/")
    public String main(Model model) {

        JSONArray uplist = sservice.getMain(1);
        JSONArray downlist = sservice.getMain(2);
        model.addAttribute("uplist", uplist);
        model.addAttribute("downlist", downlist);

        for (int i = 0; i < uplist.size(); i++) {

            JSONObject jsonObject = (JSONObject) uplist.get(i);
            String ss = jsonObject.get("statnTid").toString();
            System.out.println(ss);
        }
        System.out.println(uplist);
        return "main";
    }
    @SneakyThrows
    @GetMapping("/searchcontroller/{inputtext}")
    public String searchcontroller(@PathVariable("inputtext")String inputtext,Model model ){
        JSONArray uplist = sservice.getSearch(inputtext, 1);
        JSONArray downlist = sservice.getSearch(inputtext, 2);
        JSONArray outlist = sservice.getSearch(inputtext, 3);
        JSONArray inlist = sservice.getSearch(inputtext, 4);
        System.out.println(inlist);
        model.addAttribute("uplist", uplist);
        model.addAttribute("downlist", downlist);
        model.addAttribute("outlist", outlist);
        model.addAttribute("inlist", inlist);
        System.out.println("inlist : " + inlist);
        if(uplist == null && downlist == null && outlist == null && inlist == null){
            return "redirect:/";
        }
        return "main";
    }



}
