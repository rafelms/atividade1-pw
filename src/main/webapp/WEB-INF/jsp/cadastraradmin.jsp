<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Admin</title>
</head>
<body>

<h2>Cadastrar Novo Administrador</h2>

<!--se nao estiver vazio, exibe a mensagem-->
<c:if test="${not empty mensagemErro}">
    <p style="color: red; font-weight: bold;">${mensagemErro}</p>
</c:if>
<c:if test="${not empty mensagemSucesso}">
    <p style="color: green; font-weight: bold;">${mensagemSucesso}</p>
</c:if>

<form action="cadastraradmin" method="post">
    <label>Nome: <input type="text" name="nome" required></label><br><br>
    <label>Login: <input type="text" name="login" required></label><br><br>
    <label>Senha: <input type="password" name="senha" required></label><br><br>
    <input type="submit" value="Criar Administrador">
</form>

<hr>
<p>
    <a href="gerenciarcategorias"><button>Gerenciar Categorias</button></a>
    <a href="vertarefas"><button>Voltar para Tarefas</button></a>
    <a href="editarusuario"><button>Editar Usuario</button></a>
    <a href="sair"><button>Logout</button></a>
</p>

</body>
</html>