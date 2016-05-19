<ul id="tt" class="easyui-tree" data-options="url:'${application.getContextPath()}/report/choose?serialno=${serialno!}&flowid=${flowid!}&s_id=${s_id!}&areaid=${areaid!}',animate:true,checkbox:true,
onlyLeafCheck:true,
onSelect: function(rec){  
            var url = '${application.getContextPath()}/report/choose?serialno=${serialno!}&s_id=${s_id!}&areaid=${areaid!}&id='+rec.id;  
            $('#tt').combobox('reload', url);  
        }"></ul>