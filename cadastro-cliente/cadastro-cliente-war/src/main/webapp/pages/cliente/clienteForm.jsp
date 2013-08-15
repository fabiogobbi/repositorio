<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div id="conteudo_baixo">
	<h2>Conteúdo</h2>
	<form:form method="get" action="/cadastro_cliente/cliente/clienteEfetiva" modelAttribute="cliente" >
	
		<form:hidden path="ativo" />
        <form:hidden path="tipoAcesso" />
		<input type="hidden" id="tipoAcao" name ="tipoAcao" value="${tipoAcao}">
		
		<form:label path="email" >E-mail</form:label>
	    <form:input path="email" readonly="true"/>
	    <br/><br/>
	    
	    <form:label path="senha">Senha</form:label>
	    <form:password path="senha" />
	    <br/><br/>
	    
	    <form:label path="confirmaSenha">Confirmar Senha</form:label>
	    <form:password path="confirmaSenha" />
	    <br/><br/>
		
		<form:label path="cpf">CPF</form:label>
		<form:input path="cpf" />
		<br/><br/>
		
		<form:label path="nome">Nome</form:label>
		<form:input path="nome" />
		<br/><br/>
		
		<form:label path="sobreNome">Sobre Nome</form:label>
		<form:input path="sobreNome" />
		<br/><br/>
		
		<form:label path="dataNascimento">Data de Nascimento</form:label>
	    <form:input path="dataNascimento" />
	    <br/><br/>
	    
	    <form:label path="sexo">Sexo</form:label>
	    <form:radiobutton path="sexo" value="1"/>Masculino 
	    <form:radiobutton path="sexo" value="2"/>Feminino
	    <br/><br/>
		<br/>
		
		
		<input type="button" value="Cancelar" class="button" id="btnCancelar"/>
		<input type="submit" value="Confirma" class="button" id="btnConfirmar"/>
		
	</form:form>
	
	  <script type="text/javascript">
                $(function() {
                       $("#btnCancelar").click(function(){
                           $("#cliente").attr("action","${urlCancelar}");
                           $("#cliente").submit();   
                       });


                       $("#btnConfirmar").click(function(){
                    	   event.preventDefault();
                           if(ValidaSenha()){
                               $("#cliente").submit();   
                           }
                       });

                       
                       function ValidaSenha(){
                    	    if($("#senha").val() != $("#confirmaSenha").val()){
                    	        alert("Senhas não conferem.");
                    	        return false;
                        	}else{
                        		return true;
                            }
                       }

                       
                });
         </script>
	
</div>