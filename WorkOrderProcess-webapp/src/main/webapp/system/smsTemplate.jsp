<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title></title>
<%@include file="/common/s3.jsp"%>
<%@include file="/common/page.jsp"%>
</head>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">系统设置</a></li>
			<li><a href="#">系统配置</a></li>
			<li><a href="#">模版管理</a></li>
			<li><a href="#">短信模版</a></li>
		</ul>
	</div>


	<div class="formbody">
		<div id="usual1" class="usual">
			<div class="itab">
				<ul>
					<li><a href="#tab1" onclick="mail()">邮件模版</a></li>
					<li><a href="#tab2" class="selected" onclick="sms()">短信模版</a></li>
				</ul>
			</div>

			<div class="tabson">
				<div class="tools">
					<ul class="toolbar">
						<li class="click"><a href="smsTemplate_input.do?action=view"> <span><img src="${cdnPrefix}/template/images/t01.png" /></span>新增模版
						</a></li>
						<li class="click update"><a href="javascript:void(0);"> <span><img src="${cdnPrefix}/template/images/t02.png" /></span>修改模版
						</a></li>
						<li class="click delete"><a href="javascript:void(0);"><span><img src="${cdnPrefix}/template/images/t03.png" /></span>删除模版</a></li>
						<li class="click"><a href="javascript:void(0);"><span><img src="${cdnPrefix}/template/images/t05.png" /></span>短信配置</a></li>
					</ul>
				</div>
				<form id="form" name="smsTemplate" method="get" action="smsTemplate.do">
					<table class="tablelist">
						<thead>
							<tr>
								<th><input name="" id="selectAll" type="checkbox" value="" /></th>
								<th>编号</th>
								<th>名称</th>
								<th>信息</th>
								<th>服务器</th>
								<th>创建时间</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.result}" var="item">
								<tr>
									<td><input name="checkbox" type="checkbox" value="${item.id }" /></td>
									<td>${item.id }</td>
									<td>${item.smsName }</td>
									<td>${item.smsContent }</td>
									<td>${item.smsServer }</td>
									<td><fmt:formatDate value="${item.creationTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</form>
				
				<div class="pagin">
					<div class="message" >
						共<i class="blue">${page.totalCount }</i>条记录，当前显示第&nbsp;<i class="blue">${page.pageNo }&nbsp;</i>页
						<div id="tcdPageCode" class="tcdPageCode"  style="float:right; margin-top: 10px; margin-bottom: 10px;"></div>
					</div>
				</div>
				
			</div>
		</div>
	</div>
<script type="text/javascript">
	function mail(){
		window.location.href="${ctx }/system/mailTemplate.jsp";
	}
	function sms(){
		window.location.href="smsTemplate.do";
	}
	
	$(function(){
		//删除
		$(".delete").click(function(){
			var list=new Array;
			$("input[name='checkbox']:checked").each(function(){
				list.push($(this).attr("value"));
			});
			$.ajax({  
		        type: "POST",  
		        url: "smsTemplate_delete.do",  
		        traditional: true,  
		        data:{  
		            smsIds:list  
		        },  
		        success: function (data) {  
        		  popup({type:'success',msg:"操作成功",delay:2000,callBack:function(){
	        		 window.location.href="smsTemplate.do";
	              }}); 
		        },
		        error: function(){
		        	 popup({type:'error',msg:"操作失败",delay:2000,bg:true,clickDomCancel:true});
		        }
		    });  
		})
		
		//限制修改只能选择一条数据
		$(".update").click(function(){
			 if($("input[name='checkbox']:checked").length ==1){
				var id=$("input[name='checkbox']:checked").attr("value");
				window.location.href="smsTemplate_input.do?action=view&id="+id;
			}else{
				$(".update a").attr("href","javascript:void(0);");
				popup({type:'error',msg:"只能选择一条数据！",delay:null,bg:true,clickDomCancel:true});
			} 
		});
	})
</script>
</body>
</html>