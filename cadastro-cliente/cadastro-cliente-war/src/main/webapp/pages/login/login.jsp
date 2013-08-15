<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div id="conteudo">
	<form id="formLogin" action="<spring:url value="/j_spring_security_check"/>" method="get">
	    <h2>Login</h2>
	    <br/>
	    Digite seu email:<br/>
	    <input type="text" id="j_username" name="j_username">
	    <br/>
	    <input type="radio" name="novoAcesso" id="cadastro" value="true">Quero me cadastrar.
	    <br/>
	    <input type="radio" name="novoAcesso" id="login" value="false">Minha senha &eacute;:
	    <input type="password" id="j_password" name="j_password">
	    <br/>
	    <br/>
	    <input type="button" value="Confirmar" class="button" id="btnLogin">
	    <input type="button" value="Esqueci Minha Senha" class="button" id="btnSenha">
	    <br/>
	</form>
	
    <script type="text/javascript">
		$(function(){
		    $("#btnLogin").click(function(){
		        if($("#cadastro").is(':checked')){
		        	if($("#j_username").val() == ""){
		        	    alert("Para inicar o cadastro, digite seu email.");
		        	    return false;
		        	}else{
			            $("#formLogin").attr("action","<spring:url value="/cliente/clienteForm"/>");
		        	}
		        }else if($("#login").is(':checked')){
		        	$("#formLogin").attr("method","post");
		            $("#formLogin").attr("action","<spring:url value="/j_spring_security_check"/>");
		        }else{
		            alert("selecione");
		            return false;
		        
		        }
		        $("#formLogin").submit();   
		    });
		    
            $("#btnSenha").click(function(){
            	if($("#j_username").val() == ""){
            	    alert('Digite o email.');
            	    return false;
            	}else{
	                $("#formLogin").attr("action","<spring:url value="/email/resetSenha"/>");
	                $("#formLogin").submit(); 
            	}  
            });
		});
	</script>
	
	
	
	
	
</div>
