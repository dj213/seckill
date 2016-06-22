package org.seckill.web;

import java.util.Date;
import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillClosedException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Controller
@RequestMapping("/seckill")
public class SeckillController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	SeckillService seckillService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model) {
		List<Seckill> list = seckillService.getSeckillList();
		model.addAttribute("list", list);
		return "list";
	}
	
	@RequestMapping(value="/{seckillId}/detail",method=RequestMethod.GET)
	public String detail(Model model, @PathVariable(value="seckillId")Long seckillId) {
		if (seckillId==null) {
			return UrlBasedViewResolver.REDIRECT_URL_PREFIX+"/seckill/list";
		}
		Seckill seckill = seckillService.getById(seckillId);
		if (seckillId==null) {
			return UrlBasedViewResolver.FORWARD_URL_PREFIX+"/seckill/list";
		}		
		model.addAttribute("seckill", seckill);
		return "detail";
	}
	
	@ResponseBody
	@RequestMapping(value="/{seckillId}/detail",
		method=RequestMethod.POST,
		produces={"application/json;charset=utf-8"})
	public SeckillResult<Exposer> exposer(@PathVariable(value="seckillId")Long seckillId) {
		SeckillResult<Exposer> result=null;
		try {
			Exposer exposer = seckillService.exportSeckillUrl(seckillId);
			result = new SeckillResult(true, exposer);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			result = new SeckillResult(false, e.getMessage());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/{seckillId}/{md5}/execution",
	method=RequestMethod.POST,
	produces={"application/json;charset=utf-8"})
	public SeckillResult<SeckillExecution> execute(
			@PathVariable(value="seckillId")Long seckillId,
			@PathVariable(value="md5")String md5,
			@CookieValue(value="userPhone",required=false)Long userPhone) {
		if (userPhone == null) {
			return new SeckillResult<SeckillExecution>(false, "未注册");
		}
		SeckillResult<SeckillExecution> result=null;
		try {
			SeckillExecution excuteSeckill = seckillService.excuteSeckill(seckillId, userPhone, md5);
			result = new SeckillResult<SeckillExecution>(true, excuteSeckill);
		} catch (RepeatKillException e) {
			SeckillExecution excuteSeckill = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
			result = new SeckillResult<SeckillExecution>(true, excuteSeckill);
		} catch (SeckillClosedException e) {
			SeckillExecution excuteSeckill = new SeckillExecution(seckillId, SeckillStateEnum.END);
			result = new SeckillResult<SeckillExecution>(true, excuteSeckill);
		} catch (SeckillException e) {
			SeckillExecution excuteSeckill = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
			result = new SeckillResult<SeckillExecution>(true, excuteSeckill);			
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/time/now",
	method=RequestMethod.GET,
	produces={"application/json;charset=utf-8"})
	public SeckillResult<Long> now() {
		Date now = new Date();
		return new SeckillResult<Long>(true, now.getTime());
	}
	
	
}
