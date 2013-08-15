<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div id="conteudo_baixo">
	<h2>Conteúdo</h2>
	<form:form method="get" action="/cadastro_cliente/endereco/enderecoEfetiva" modelAttribute="endereco" name="">
	
	<input type="hidden" id="tipoAcao" name ="tipoAcao" value="${tipoAcao}">
	<form:hidden path="id" />
	
	<form:label path="descricao" >Descri&ccedil;&atilde;o</form:label>
    <form:input path="descricao" />
    <br/><br/>
    
    <form:label path="cep">Cep</form:label>
    <form:input path="cep" />
    <br/><br/>
    
    <form:label path="logradouro">Logradouro</form:label>
    <form:input path="logradouro" />
    <br/><br/>
    
    <form:label path="numero">N&uacute;mero</form:label>
    <form:input path="numero" />
    <br/><br/>
	
	<form:label path="complemento">Complemento</form:label>
	<form:input path="complemento" />
	<br/><br/>
	
	<form:label path="bairro">Bairro</form:label>
	<form:input path="bairro" />
	<br/><br/>
	
	<form:label path="cidade">Cidade</form:label>
    <form:input path="cidade" />
    <br/><br/>
    
    <form:label path="estado">Estado</form:label>
    <form:input path="estado" size="2" />
    <br/><br/>
	<br/><br/>
	
	<input type="button" value="Cancelar" class="button" id="btnCancelar"/>
	<input type="submit" value="Confirma" class="button"/>
	
	</form:form>
	
	
     <script type="text/javascript">
                $(function() {
                       $("#cep").blur(function() {
                              var cep = $("#cep").val();
                              var controlCorreioAPI = "http://cep.correiocontrol.com.br/" + cep + ".json";

                              $.getJSON(controlCorreioAPI, function() {
                              })
                              .fail(function() {
                                      alert("CEP não encontrado.");
                              })
                              .done(function(json) {
                                    $("#logradouro").val(json.logradouro);
                                    $("#bairro").val(json.bairro);
                                    $("#cidade").val(json.localidade);
                                    $("#estado").val(json.uf);
                              });                              
                       });



                       $("#btnCancelar").click(function(){
                           $("#endereco").attr("action","<spring:url value="/endereco/enderecoLista"/>");
                           $("#endereco").attr("method","get");
                           $("#endereco").submit();   
                       });

                       
                });
         </script>
	
	
</div>