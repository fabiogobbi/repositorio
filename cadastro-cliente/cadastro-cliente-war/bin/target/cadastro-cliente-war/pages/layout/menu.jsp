<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<h3>Menu</h3>
<a href="<spring:url value="/cliente/clienteAtualiza"/>">
    <p>Meus Dados</p>
</a>
<a href="<spring:url value="/endereco/enderecoLista"/>">
    <p>Meus Endere&ccedil;os</p>
</a>
<a href="<spring:url value="/j_spring_security_logout"/>">
    <p>Sair</p>
</a>


<script type="text/javascript">
$(function(){
    $("#meusDados").click(function(){
        $.post("<spring:url value="/endereco/enderecoLista"/>",
        		function(){
                        window.location = "/pages/ajax/clienteFormAjax.jsp";
                }
         );
    });
});
        
</script>