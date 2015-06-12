<%--
  Created by IntelliJ IDEA.
  User: Senhui
  Date: 4/14/2015
  Time: 3:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" language="java" %>
<div class="con">
  <table width="100%" border="0" cellspacing="1" cellpadding="0" style="height:97%;background: url('${ctx}/static/img/admin/welcome.jpg') no-repeat right bottom;">
    <tr>
      <td width="56%" valign="top">
        <div id="newcomm">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size: 12px;">
            <tr>
              <td width="18%" height="96" valign="middle">
                <img style="margin-left:3px;" src="${ctx}/static/img/admin/main-logo.jpg" width="92" height="92" />
              </td>
              <td width="82%" valign="middle">
                <p style="font-weight:bold; color:#039" id="vertext">当前版本：1.b.28&nbsp;&nbsp;&nbsp;&nbsp;更新时间：2015/5/6</p>              </td>
            </tr>
          </table>
        </div>

        <div class="tb-line"></div>

        <div id="comm" style="padding:2px">
          <table class="infobox table-border" width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size: 12px;">
            <tr style="height:23px; line-height:23px;">
              <td width="18%">服务器系统</td>
              <td width="32%">${osname}</td>
              <td width="18%">服务器IP</td>
              <td width="32%">${ip}</td>
            </tr>
            <tr style="height:23px; line-height:23px;">
              <td width="18%">编码集</td>
              <td width="32%">${encoding}</td>
              <td width="18%">当前路径</td>
              <td width="32%">${curpath}</td>
            </tr>
            <tr style="height:23px; line-height:23px;">
              <td width="18%">路径字符</td>
              <td width="32%">${filesep}</td>
              <td width="18%">JDK版本</td>
              <td width="32%">${version}</td>
            </tr>
            <tr style="height:23px; line-height:23px;">
              <td width="18%">时区</td>
              <td width="32%">${timezon}</td>
              <td width="18%">MAC</td>
              <td width="32%">${mac}</td>
            </tr>
            <tr style="height:23px; line-height:23px;">
              <td width="18%">服务组件</td>
              <td width="32%">${serverinfo}</td>
              <td width="18%">运行版本</td>
              <td width="32%">${servletapi}</td>
            </tr>
            <tr style="height:23px; line-height:23px;">
              <td width="18%">访问入口</td>
              <td colspan="3">${context}</td>
            </tr>
          </table>
        </div>

        <div class="tb-line"></div>

      </td>
    </tr>
  </table>
</div>
