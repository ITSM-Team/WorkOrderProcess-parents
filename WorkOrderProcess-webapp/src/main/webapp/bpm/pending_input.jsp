<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html >
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title></title>
<%@include file="/common/s3.jsp"%>
<script type="text/javascript" src="${cdnPrefix}/jQueryPrint/js/vendor/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${cdnPrefix}/jQueryPrint/js/jQuery.print.js"></script>
<style type="text/css">
.wd{ margin-left: 20px;  font-size: 14px; }
.wt{ margin-left: 3px;  font-size: 14px; font-weight: bold; }
.tipbtn{ margin-top:25px; margin-left:35%; }
</style>
<script type="text/javascript">
	$(function(){
		//遍历判断是否配置有操作按钮，无则隐藏div
		$(".ibtn").each(function(){
			if(!$(this).hasClass("isShow")){
				$(".operating").removeClass("isShow");
			}
		});
		
		$(".off").each(function(){
			if(!$(this).hasClass("isShow")){
				$(".offOperating").removeClass("isShow");
			}
		});
	})
</script>
</head>
<body>
<div class="place no-print">
	<span>位置：</span>
	<ul class="placeul">
		<li><a href="#">流程首页</a></li>
		<li><a href="#">待处理</a></li>
		<li><a href="#">修改</a></li>
	</ul>
	<div class="offOperating isShow">
		<ul class="oaPlaceul">
			<li class="off"><a href="#" onclick="jQuery.print()"><span><img src="${cdnPrefix}/template/images/off_1.png" title="打印" class="helpimg" /></span>打印</a></li>
			<li class="off"><a href="#"><span><img src="${cdnPrefix}/template/images/off_2.png" title="流程图" class="helpimg" /></span>流程图</a></li>
			<li class="off"><a href="#"><span><img src="${cdnPrefix}/template/images/off_3.png" title="导出" class="helpimg" /></span>导出</a></li>
		</ul>
	</div>
		
</div>

	<div class="formbody">
		<div class="operating no-print isShow">
			<div class="formtitle">
				<span>操作</span>
			</div>
			<div class="toolsli">
				<a class="ibtn"><img src="${cdnPrefix}/template/images/ope_1.png">改派</a>
				<a class="ibtn"><img src="${cdnPrefix}/template/images/ope_2.png">回退</a>
				<a class="ibtn"><img src="${cdnPrefix}/template/images/ope_3.png">添加关联关系</a>
				<a class="ibtn"><img src="${cdnPrefix}/template/images/ope_4.png">强制关单</a>
				<a class="ibtn"><img src="${cdnPrefix}/template/images/ope_5.png">催办</a>
				<a class="ibtn"><img src="${cdnPrefix}/template/images/ope_6.png">抄送</a>
				<a class="ibtn"><img src="${cdnPrefix}/template/images/ope_7.png">创建知识</a>
				<a class="ibtn"><img src="${cdnPrefix}/template/images/ope_8.png">取消关注</a>
				<a class="ibtn"><img src="${cdnPrefix}/template/images/ope_9.png">关注人</a>
				<a class="ibtn"><img src="${cdnPrefix}/template/images/ope_2.png">追回</a>
				<a class="ibtn"><img src="${cdnPrefix}/template/images/ope_10.png">快照</a>
				<a class="ibtn"><img src="${cdnPrefix}/template/images/ope_11.png">撤销</a>
			</div>
		</div>
		<div class="formtitle">
			<span>信息</span>
		</div>
		<div class="toolsli">
			<label class="wd">上个环节：</label><label class="wt">部门领导审批</label>
			<label class="wd">下个环节：</label><label class="wt">人事部审批</label>
		</div>

		<div class="formtitle">
			<span>表单</span>
		</div>
		<div class="toolsli">
			<ul class="forminfo">
				<li>
					。。。。
				</li>
				<li>
					<label style="width: 45px;">意见:</label>
					<textarea name="" class="textinput" style="width: 800px;height: 50px;"></textarea>
				</li>
			</ul>
		</div>
		
		<div class="formtitle">
			<span>表格</span>
		</div>
		<div class="toolsli">
			<table class="tablelist">
				<thead>
					<tr>
						<th>环节</th>
						<th>审批人</th>
						<th>时间</th>
						<th>结果</th>
						<th>意见</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>填写请假单</td>
						<td>lisa</td>
						<td>2018-04-26 09:55:09</td>
						<td>提交</td>
						<td>--</td>
					</tr>
	
					<tr>
						<td>部门领导审批</td>
						<td>adan</td>
						<td>2018-04-26 10:07:39</td>
						<td>提交</td>
						<td>--</td>
					</tr>
				</tbody>
			</table>
		
		
			<div class="tipbtn no-print" >
				后台获取按钮
				<!-- <input name="" type="button" class="sure" value="提交" />&nbsp; 
				<input name="" type="button" class="sure" value="保存到草稿" /> -->
			</div>
		</div>

	</div>
</body>
</html>