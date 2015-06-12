<%--
  Created by IntelliJ IDEA.
  User: Senhui
  Date: 4/15/2015
  Time: 3:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" language="java" %>

<script language="javascript">var sdate = '0000-00-00';
var edate = '2015-04-15';
$(function(){
  var cw = $("#BugCon").width();
  var th = $(".top").height();
  th = 111-th;
  var wh = $(window).height()-th;
  var ch = $("body").height();
  var pr = '50';
  var pn = false;
  var tit = '';
  if(pr>0){
    pn = true;
  }
  $("#Bug").datagrid({
    //title:'BUG百科',
    height:wh,
    autoRowHeight:false,
    singleSelect:true,
    striped:true,
    rownumbers:true,
    pagination:pn,
    showFooter:true,
    prototype:true,
    pageSize:pr,
    pageList:[30,50,80,100,100000000000],
    method:'get',
    //url:'/dream/index.php?g=Admin&m=Bug&a=index&json=1',
    fitColumns:Number('1'),
    nowrap:Number('1'),
    selectOnCheck:false,
    checkOnSelect:true,
    onBeforeLoad: function(){
      if($("#BugCon .datagrid-toolbar table tr #sersSearchBug").length==0){
        var grid = $("#BugCon .datagrid-toolbar table tr");
        var ldata = '<td>'+$("#selectInputBug").html()+'</td>';
        grid.append(ldata);

        $("#sersSearchBug").change(function(){
          var idd = $(this).val();
          $.post('/dream/index.php?g=Admin&m=Bug&a=change&act=type', {val:idd}, function(data){
            $("#Bug").datagrid('reload');
          });
        });

        $("#seleBugAddtimeST").datebox({
          onSelect:function(date){
            var gen = $("#seleBugAddtimeEN").datebox('getValue');
            var en = gen?gen:'2015-04-15';
            sdate = date.getFullYear()+"-"+pad((date.getMonth()+1),2)+"-"+pad(date.getDate(),2);
            var idd = sdate+'|'+en;
            $.post('/dream/index.php?g=Admin&m=Bug&a=change&act=addtime', {val:idd}, function(data){
              $("#Bug").datagrid('reload');
            });
          }

        });

        $("#seleBugAddtimeEN").datebox({
          onSelect:function(date){
            var gst = $("#seleBugAddtimeST").datebox('getValue');
            var st = gst?gst:'0000-00-00';
            edate = date.getFullYear()+"-"+pad((date.getMonth()+1),2)+"-"+pad(date.getDate(),2);
            var idd = st+'|'+edate;
            $.get('/dream/index.php?g=Admin&m=Bug&a=change&act=addtime', {val:idd}, function(data){
              $("#Bug").datagrid('reload');
            });
          }
        });
      }

      if($("#BugCon .datagrid-toolbar #BugSearch").length==0){
        var toolbar = $("#BugCon .datagrid-toolbar");
        toolbar.css({
          "overflow":"hidden"
        });
        var tooltable = $("#BugCon .datagrid-toolbar table");
        tooltable.css({
          "float":"left"
        });
        var tw = toolbar.width();
        tooltable.addClass("tname");
        var sw = $("#BugCon .datagrid-toolbar .tname").width();
        //alert(sw);

        var ww = tw-sw-8;
        var date = '<table cellspacing="0" cellpadding="0" style="width:'+ww+'px; height:28px; float: left;" class="pname"><tr><td align="right">'+$("#selectReloadBug").html()+'</td></tr></table>';
        toolbar.append(date);

        $('#BugSearch').searchbox({
          searcher:function(value,name){
            $.post('/dream/index.php?g=Admin&m=Bug&a=change&act='+name+'&mode=like', {val:value}, function(data){
              $("#Bug").datagrid('reload');
            });
          },
          menu:'#BugSearchSon',
          prompt:'请输入关键字'
        });
      }

      //$("#seleBugAddtimeST").datebox('setValue',sdate);
      // $("#seleBugAddtimeEN").datebox('setValue',edate);
    },
    onHeaderContextMenu:function(e,f){
      if(f!='title'){
        $("#searchBug").dialog({
          title:'快速搜索',
          resizable:true,
          width:430,
          height:80,
          href:'/dream/index.php?g=Admin&m=Bug&a=search&field='+f
        });
      }
      e.preventDefault();
    },
    onDblClickRow:function(e,rowIndex,rowData){
      //var se = $(this).datagrid('getSelected');
      var se = $("#Bug").datagrid('getChecked');
      var se_len = se.length;
      var idd = se[0]['id'];
      if(se_len==1){
        $("<div/>").dialog({
          title:'百科详情',
          resizable:true,
          width:955,
          height:ch-80,
          href:'/dream/index.php?g=Admin&m=Bug&a=detail&id='+idd,
          onOpen:function(){
            cancel['BugDetail'] = $(this);
          },
          onClose:function(){
            //$("#Bug").datagrid('reload');
            $(this).dialog('destroy');
            cancel['BugDetail'] = null;
          }
        });
      }
    },
    onUncheck:function(i,d){
      $("#Bug").datagrid('unselectRow',i);
    },
    toolbar:[{
      iconCls: 'icon-add',
      text : '新增',
      handler: function(){
        $("#addBug").dialog({
          title:'新增百科',
          resizable:true,
          width:885,
          height:ch-80,
          href:'/dream/index.php?g=Admin&m=Bug&a=add&act=add',
          onOpen:function(){
            cancel['Bug'] = $(this);
          },
          onClose:function(){
            //$(this).dialog('destroy');
            //$("#Bug").datagrid('reload');
            cancel['Bug'] = null;
          }
        });
      }
    },'-',{
      iconCls: 'icon-edit',
      text : '编辑',
      handler: function(){
        //var se = $("#Bug").datagrid('getSelected');
        var se = $("#Bug").datagrid('getChecked');
        var se_len = se.length;
        var idd = se[0]['id'];
        if(se_len==1){
          $("#addBug").dialog({
            title:'编辑百科',
            resizable:true,
            width:885,
            height:ch-80,
            href:'/dream/index.php?g=Admin&m=Bug&a=add&act=edit&id='+idd,
            onOpen:function(){
              cancel['Bug'] = $(this);
            },
            onClose:function(){
              //$(this).dialog('destroy');
              //$("#Bug").datagrid('reload');
              cancel['Bug'] = null;
            }
          });
        }else if(se_len>1){
          $.messager.alert('提示','不能同时编辑两行数据！','warning');
        }
      }
    },'-',{
      iconCls: 'icon-cancel',
      text : '删除',
      handler: function(){
        var se = $("#Bug").datagrid('getChecked');
        var s = "";
        for (var property in se) {
          s = s + se[property]['id']+',' ;
        }
        if(s){
          $.messager.confirm('提示','确定删除吗！',function(r){
            if(r==true){
              $.messager.progress();
              $.post('/dream/index.php?g=Admin&m=Bug&a=del',{id:s}, function(data){
                $.messager.progress('close');
                if(data==1){
                  $.messager.alert('提示','删除数据成功！','info',function(){
                    $("#Bug").datagrid('reload');
                  });
                }else if(data==0){
                  $.messager.alert('提示','删除数据失败！','warning');
                }else if(data==2){
                  $.messager.alert('提示','只能删除自己所新增的数据！','warning');
                }else{
                  $.messager.alert('提示','您没有删除权限','warning');
                }
              });
            }
          });
        }
      }
    },'-',{
      iconCls: 'icon-search',
      text : '高级搜索',
      handler: function(){
        $("#searchBug").dialog({
          title:'高级搜索',
          resizable:true,
          width:500,
          height:220,
          href:'/dream/index.php?g=Admin&m=Bug&a=advsearch'
        });
      }
    },'-',{
      iconCls: 'icon-reload',
      text : '重载',
      handler: function(){
        /*$.get('/dream/index.php?g=Admin&m=Bug&a=clear', function(data){
          $("#sersSearchBug").val(0);
          $("#seleBugAddtimeST").datebox('setValue','');
          $("#seleBugAddtimeEN").datebox('setValue','');
          $("#BugSearch").searchbox('setValue','');
          $("#Bug").datagrid('reload');
        });*/
      }
    }],
    frozenColumns:[[
      {checkbox:true},
      {field:'title',title:'问题描述',width:550,sortable:true}
    ]],
    columns:[[
      {field:'type',title:'<span class="tit-bl" title="单击右键，进入快速搜索">问题类型</span>',width:100,sortable:true},
      {field:'project',title:'<span class="tit-bl" title="单击右键，进入快速搜索">代表机型</span>',width:150,sortable:true},
      {field:'os',title:'<span class="tit-bl" title="单击右键，进入快速搜索">系统平台</span>',width:110,sortable:true},
      {field:'username',title:'<span class="tit-bl" title="单击右键，进入快速搜索">作者</span>',width:60,sortable:true},
      {field:'addtime',title:'<span class="tit-bl" title="单击右键，进入快速搜索">创建时间</span>',width:150,sortable:true}
    ]]
  });


  var dataview = '0';
  if(dataview!='0'){
    var pager = $('#Bug').datagrid('getPager');
    pager.pagination({
      layout: 'list,sep,first,prev,sep,manual,sep,next,last,sep,refresh',
      displayMsg: '共{total}记录'
    });
  }

  $("#rightTabs").tabs({
    onClose:function(t,i){
      $.ajaxSetup({
        async : false
      });
      if(t=='BUG百科'){

      }
      $.ajaxSetup({
        async : true
      });
    }
  });

  $("#rightTabs").tabs('select','BUG百科');
});
</script>
<div id="BugCon" class="con" onselectstart="return false;" style="-moz-user-select:none;">
    <table id="Bug"></table>
</div>
<div id="searchBug"></div>
<div id="addBug"></div>
<div id="selectInputBug" style="display:none"><span class="datagrid-btn-separator-nofloat"
                                                    style="margin-right:2px;"></span><select id="sersSearchBug">
    <option value="0" style="color:#7d7d7d">所有类型</option>
    <option value="1">重大问题</option>
    <option value="2">硬件问题</option>
    <option value="3">软件问题</option>
</select><span class="datagrid-btn-separator-nofloat" style="margin-right:2px;"></span><input id="seleBugAddtimeST"
                                                                                              name="addtime[]" size="10"
                                                                                              type="text"></input>
    &nbsp;至&nbsp;
    <input id="seleBugAddtimeEN" name="addtime[]" size="10" type="text"></input></div>
<div id="selectReloadBug" style="display:none"><input id="BugSearch" AUTOCOMPLETE="off" style="width:280px;"></input>

    <div id="BugSearchSon" style="width:120px">
        <div data-options="name:'title'">问题描述</div>
        <div data-options="name:'content_index'">全文搜索</div>
        <div data-options="name:'project'">代表机型</div>
        <div data-options="name:'os'">系统平台</div>
    </div>
</div>
