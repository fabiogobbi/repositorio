<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div id="conteudo_baixo">
	<h2>Login pelo Facebook</h2>
	<br/>
	Voc&ecirc; tamb&eacute;m pode entrar no Kwik-E-Mart usando sua conta do Facebook.
	<br/>
	<br/>
	<input type="button" value="Login pelo Facebook" class="button" id="btnFacebook">
	<br/>
	<script type="text/javascript">
        $(function(){
            $("#btnFacebook").click(function(){
                 $("#formLogin").attr("action","<spring:url value="/login/loginFacebook"/>");
                 $("#formLogin").submit(); 
            });
        });
    </script>
</div>
