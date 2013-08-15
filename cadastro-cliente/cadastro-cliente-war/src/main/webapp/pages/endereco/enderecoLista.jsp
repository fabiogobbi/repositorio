<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<h4>Lista de Endere&ccedil;os</h4>

<form id="frmEndereco" action="" method="get">
	<c:forEach items="${cliente.enderecos}" var="endereco" varStatus="indice">
        <input type="radio" name="idEndereco" id="idEndereco_${indice.index}" value="${endereco.id}">	   
        ${endereco.descricao} 
	    <br/>
	</c:forEach>
	<br/>
	<input type="button" value="Adicionar" class="button" id="btnAdicionar">
	&nbsp;
    <input type="button" value="Alterar" class="button" id="btnAlterar">
	&nbsp;
	<input type="button" value="Remover" class="button" id="btnRemover">
    <br/><br/>
</form>

<script type="text/javascript">
$(function(){

	function obterEnderecoSelecionado() {
		if ($('input[name=idEndereco]:checked').val() == undefined) {
		    alert('Selecione radio');
		    return false;
		}else{
		    return true;
		}
	}

    $("#btnAdicionar").click(function(){
	        $("#frmEndereco").attr("action","/cadastro_cliente/endereco/enderecoForm");
	        $("#frmEndereco").submit();   
    });

    $("#btnAlterar").click(function(){
    	if(obterEnderecoSelecionado()){
	        $("#frmEndereco").attr("action","/cadastro_cliente/endereco/enderecoAlterar");
	        $("#frmEndereco").submit();   
        }
    });

    
    $("#btnRemover").click(function(){
    	if(obterEnderecoSelecionado()){
	    	 $("#frmEndereco").attr("action","/cadastro_cliente/endereco/enderecoExcluir");
	    	 $("#frmEndereco").submit();   
        }
    });
    
});
        
</script>