  <form method="post" id="dataForm" class="form">
    <ul>
      <li><span style="text-align:left;margin-left:6px;width:40px;">地址：</span>
        <p>
         <textarea name="address" id="address" style="border:1px solid #D3D3D3;height: 28px;line-height:28px; width: 350px;font-size:13px;" maxlength="100">${address!} </textarea>
        </p>
      </li>
    </ul>
  </form>
<script>
$("#address").val($("#add_span").html().replaceAll('&gt;','>').replaceAll('&lt;','<'));
</script>
