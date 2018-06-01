<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title></title>
<%@include file="/common/s3.jsp"%>
<link rel="stylesheet" href="${cdnPrefix}/simpleTree/css/SimpleTree.css"/>
<script type="text/javascript" src="${cdnPrefix}/simpleTree/js/SimpleTree.js" ></script>
<style type="text/css">
.ope { color: #056C9E;}
.ope:hover{ text-decoration: underline; }
</style>
<script type="text/javascript">
$(document).ready(function(e) {
	$(".select1").uedSelect({
		width : 200
	});
});
$(function(){
	$(".st_tree").SimpleTree({});
	$(".st_tree ul li a").click(function(){
		$("#sponsorName").val($(this).attr("title"));
	});
	
	$("#partner").click(function(){
		$(".tip").fadeIn(200);
	});
	$(".tiptop a").click(function(){
	  $(".tip").fadeOut(200);
	});

  	$(".sure").click(function(){
	  $(".tip").fadeOut(100);
	  $("#partner").val($("#sponsorName").val());
	});
	
  	/* 清除代理人 */
  	$(".position").click(function(){
  		$("#partner").val("");
  	});
});
</script>
<body>
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">流程首页</a></li>
			<li><a href="#">流程配置</a></li>
			<li><a href="#">配置</a></li>
			<li><a href="#">人员</a></li>
		</ul>
	</div>

	<div class="formbody">
		<div id="usual1" class="usual">
			<div class="itab">
				<ul>
					<li><a href="#tab1" class="selected">参与者</a></li>
					<li><a href="#tab2">分配策略</a></li>
				</ul>
			</div>
			
			<div id="tab1" class="tabson">
				<div class="formbody">
					<div class="formtitle">
						<span>返回</span>
					</div>
					<div class="toolsli">
						<input name="" type="button" class="sure" onclick="history.back();" value="返回" />
					</div>
				</div>
			
				<div class="formbody">
					<div class="formtitle">
						<span>添加</span>
					</div>
					<div class="toolsli">
						<ul class="forminfo">
							<li>
								<label>参与者:</label>
								<input name="" id="partner" type="text" class="dfinput" readonly="readonly" style="width: 200px;" />
								<img class="position" src="${cdnPrefix}/template/images/empty.png"/>
							</li>
							<li>
								<label>类型:</label>
								<div class="vocation">
									<select class="select1">
										<option>负责人</option>
										<option>候选人</option>
										<option>候选组</option>
										<option>抄送人</option>
									</select>
								</div>
							</li>
							<li></li>
						</ul>
						<input name="" type="button" class="sure" value="提交" style="margin-left: 200px;"/> 
					</div>
				</div>
				
				<div class="formbody">
					<div class="formtitle">
						<span>列表</span>
					</div>
					<div class="toolsli">
						<table class="tablelist">
							<thead>
								<tr>
									<th>编号</th>
									<th>名称</th>
									<th>类型</th>
									<th>状态</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>1130988332843008</td>
									<td>常用语:流程发起人</td>
									<td>负责人</td>
									<td>默认</td>
									<td><a href="#" class="ope">删除</a></td>
								</tr>
								<tr>
									<td>1130988332843008</td>
									<td>常用语:流程发起人</td>
									<td>负责人</td>
									<td>默认</td>
									<td><a href="#" class="ope">删除</a></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>


			<div id="tab2" class="tabson">
				<div class="formbody">
					<div class="formtitle">
						<span>返回</span>
					</div>
					<div class="toolsli">
						<input name="" type="button" class="sure" onclick="history.back();" value="返回" />
					</div>
				</div>
			
				<div class="formbody">
					<div class="formtitle">
						<span>分配策略</span>
					</div>
					<div class="toolsli">
						<ul class="forminfo">
							<li>
								<label>分配策略:</label>
								<div class="vocation">
									<select class="select1">
										<option>无</option>
										<option>当只有一人时采用独占策略</option>
										<option>资源中任务最少者</option>
										<option>资源中随机分配</option>
									</select>
								</div>
							</li>
						</ul>
						&nbsp;&nbsp;
						<input name="" type="button" class="sure" value="提交" />
					</div>
				</div>
			</div>
		</div>
		
		<div class="tip">
    	  <div class="tiptop"><span>查询参与者信息</span><a></a></div>
	      	<div class="tipbtn">
			 	<label>名称：</label><input name="" id="sponsorName" type="text" class="scinput" readonly="readonly"/>&nbsp;&nbsp;
		        <input name=""  type="button" class="sure" value="确定" />
	        </div>
	   		<div class="st_tree" style="width:350px;margin:0 auto;">
				<ul>
					<li><a href="#" ref="xtgl" title="公司">公司</a></li>
					<li><a href="#" ref="xtgl">行政部</a></li>
					<ul show="true">
						<li><a href="#" ref="yhgl" title="张三">张三</a></li>
						<li><a href="#" ref="rzck" title="李四">李四</a></li>
					</ul>
					<li><a href="#" ref="ckgl">人事</a></li>
					<ul>
						<li><a href="#" ref="kcgl" title="金">金</a></li>
						<li><a href="#" ref="shgl" title="亚当">亚当</a></li>
						<li><a href="#" ref="fhgl" title="麦克">麦克</a></li>
					</ul>
				</ul>
			</div>
	    </div>
		
		<script type="text/javascript"> 
	      $("#usual1 ul").idTabs(); 
	    </script>
	</div>
</body>
</html>