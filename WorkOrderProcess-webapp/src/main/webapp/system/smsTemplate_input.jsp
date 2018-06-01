<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title></title>
<%@include file="/common/s3.jsp"%>
<script type="text/javascript">
$(document).ready(function(e) {
    $(".select1").uedSelect({
		width : 345			  
	});
});
</script>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">系统设置</a></li>
			<li><a href="#">系统配置</a></li>
			<li><a href="#">模版管理</a></li>
			<li><a href="#">短信模版</a></li>
			<li><a href="#">新增模版</a></li>
		</ul>
	</div>

	<div class="formbody">
		<div id="usual1" class="usual">
			<div class="formbody">
				<div class="formtitle">
					<span>编辑</span>
				</div>
				<div class="toolsli">
					<form id="form" name="smsTemplate_input" method="post">
						<ul class="forminfo">
							<li>
								<label>名称:<b>*</b></label>
								<input name="smsName" type="text" class="smsName dfinput" style="width: 345px;"  value="${smsTemplate.smsName }"/>
								&nbsp;<b class="smsNameHide hide">姓名不能为空!</b>
							</li>
							<li>
								<label>短信服务器 :<b>*</b></label>
								<div class="vocation">
									<select class="smsServer select1" name="smsServer">
										<option value="">全部</option>
										<option value="1" ${smsTemplate.smsServer == '1' ? 'selected' : ''}>默认</option>
										<option value="2" ${smsTemplate.smsServer == '2' ? 'selected' : ''}>网易</option>
									</select>
								</div>
								&nbsp;<b class="smsServerHide hide">短信服务器不能为空!</b>
							</li>
							<li>
								<label>信息:<b>*</b></label>
								<textarea name="smsContent" cols="" rows="" class="smsContent textinput" value="${smsTemplate.smsContent }">${smsTemplate.smsContent }</textarea>
								&nbsp;<b class="smsContentHide hide">信息不能为空!</b>
							</li>
						</ul>
						<input name="id" type="hidden" class="id" value="${smsTemplate.id }"/>
						<div class="tipbtn" style="margin-left: 110px;">
							<input name="" type="button" class="sure" value="保存"/>&nbsp; 
							<input name="" type="button" class="cancel" onclick="history.back();" value="返回" />
						</div>
					</form>	
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		$(".sure").click(function(){
			var smsName=$(".smsName").val();
				smsServer=$(".smsServer").val();
				smsContent=$(".smsContent").val();
				id=$(".id").val();
				url="";
			if(id==""||id==null){
				url="smsTemplate_input.do?action=add";
			}else{
				url="smsTemplate_input.do?action=update";
			}	
				
			 if(smsName=="" || smsName==null){
				$(".smsNameHide").removeClass("hide");
			}else if(smsServer=="" || smsServer==null){
				$(".smsServerHide").removeClass("hide");
			}else if(smsContent=="" || smsContent==null){
				$(".smsContentHide").removeClass("hide");
			}else{
				$.ajax({  
			        type: "POST",  
			        url: url,  
			        traditional: true,  
			        data:{  
			            id:id,
			            smsName:smsName,
			            smsServer:smsServer,
			            smsContent:smsContent
			        },  
			        success: function (data) {
	        		  popup({type:'success',msg:"操作成功",delay:1000,callBack:function(){
		        		 window.location.href="smsTemplate.do";
		              }}); 
			        },
			        error: function(){
			        	 popup({type:'error',msg:"操作失败",delay:2000,bg:true,clickDomCancel:true});
			        }
			    });
			}
		})
	})
</script>
</html>