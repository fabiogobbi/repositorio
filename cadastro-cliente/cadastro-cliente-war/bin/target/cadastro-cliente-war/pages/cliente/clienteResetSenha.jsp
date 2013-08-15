<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div id="conteudo_baixo">
	<h2>Conteúdo</h2>
	<form id="cliente" action="" method="get">
	
		<label for="email">E-mail</label>
	    <input id="email" name="email" readonly="readonly" type="text" value="${email}"/>
	    <br/><br/>
	    
	    <label for="senha">Nova Senha</label>
	    <input id="senha" name="senha" type="password" value=""/>
	    <br/><br/>
	    
	    <label for="confirmaSenha">Confirmar Nova Senha</label>
	    <input id="confirmaSenha" name="confirmaSenha" type="password" value=""/>
	    <br/><br/>
		
		<input type="button" value="Cancelar" class="button" id="btnCancelar"/>
		<input type="submit" value="Confirma" class="button" id="btnConfirmar"/>
		
	</form>
	
	  <script type="text/javascript">
                $(function() {


	                    $("#btnConfirmar").click(function(){
	                    	event.preventDefault();
		                    if(ValidaSenha()){
		                        $("#cliente").attr("action","/cadastro_cliente/cliente/clienteSenhaEfetiva");
		                        $("#cliente").attr("method","get");
		                        $("#cliente").submit();  
		                    } 
		               });

                    
                       $("#btnCancelar").click(function(){
                           $("#cliente").attr("action","/cadastro_cliente/");
                           $("#cliente").attr("method","get");
                           $("#cliente").submit();   
                       });

                       function ValidaSenha(){
                    	   if($("#senha").val() == "" || $("#confirmaSenha").val() == ""){
                               alert("Digite a senha.");
                               return false;
                    	   }
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