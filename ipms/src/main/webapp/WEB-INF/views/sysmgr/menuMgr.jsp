<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/common/include/base-taglib.jsp"%>
<%@include file="/WEB-INF/views/common/include/base-header.jsp"%><div class="aui-grid-12">
    <div class="ipms-content" style="margin-left:0px;">
        <!--维度选择区域 start-->
        <div class="aui-dimpanel">

            <div class="aui-dimpanel-item">
                <label>菜单名称：</label>
                <input type="text" placeholder="菜单名称"></div>

            <div class="aui-dimpanel-func">
                <button type="button" class="aui-btn aui-btn-sm">查询</button>
            </div>
        </div>
        <!--维度选择区域 end-->
  </div>
  <div class="aui-grid-12" style="margin:15px 0px;text-algin:right;">
    <button type="button" class="aui-btn aui-btn-md" style="float:right;">添加菜单</button>
  </div>
  <div class="aui-grid-12">
    <table class="aui-table aui-table-bordered" id="splitTable">
        <thead>
            <tr>
                <th>菜单名称</th>
                <th>菜单类型</th>
                <th>添加时间</th>
                <th>修改时间</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <tr>
              <td>
                IP资源管理
              </td>
              <td>
                菜单类型
              </td>
              <td>
                2015-07-28 08：00
              </td>
              <td>
                2015-07-28 08：00
              </td>
              <td style="text-align:center;">
                <a href="javascript:;">查看</a>
                <a href="javascript:;">修改</a>
                <a href="javascript:;">删除</a>
              </td>
            </tr>
            <tr>
              <td>
                系统管理
              </td>
              <td>
                菜单类型
              </td>
              <td>
                2015-07-28 08：00
              </td>
              <td>
                2015-07-28 08：00
              </td>
              <td style="text-align:center;">
                <a href="javascript:;">查看</a>
                <a href="javascript:;">修改</a>
                <a href="javascript:;">删除</a>
              </td>
            </tr>
            <tr>
              <td>
                IP地址查询
              </td>
              <td>
                菜单类型
              </td>
              <td>
                2015-07-28 08：00
              </td>
              <td>
                2015-07-28 08：00
              </td>
              <td style="text-align:center;">
                <a href="javascript:;">查看</a>
                <a href="javascript:;">修改</a>
                <a href="javascript:;">删除</a>
              </td>
            </tr>
            <tr>
              <td>
                监测日志
              </td>
              <td>
                菜单类型
              </td>
              <td>
                2015-07-28 08：00
              </td>
              <td>
                2015-07-28 08：00
              </td>
              <td style="text-align:center;">
                <a href="javascript:;">查看</a>
                <a href="javascript:;">修改</a>
                <a href="javascript:;">删除</a>
              </td>
            </tr>
            <tr>
              <td>
                操作日志
              </td>
              <td>
                菜单类型
              </td>
              <td>
                2015-07-28 08：00
              </td>
              <td>
                2015-07-28 08：00
              </td>
              <td style="text-align:center;">
                <a href="javascript:;">查看</a>
                <a href="javascript:;">修改</a>
                <a href="javascript:;">删除</a>
              </td>
            </tr>
        </tbody>
    </table>
  </div>
  </div>
<%@include file="/WEB-INF/views/common/include/base-footer.jsp"%>
