<h2>  O que é ? </h2>

Projeto final em Java, JPA/Hibernate da disciplina de Linguagem de Programação 2, do Curso de Informática Subsequente do Instituto Federal da Bahia, 
Professor Fabiano Vaz.

No projeto foi implementado uma amostra do back-end de um sistema de loja de roupas, com suas respectivas classes `model` e classes `dao` com persistência 
de dados através do Hibernate, foi utilizado a ferramenta de automação e gerenciamento de projetos `Maven` neste projeto, como configuração padrão utilizei
o Banco de Dados MySql, porém alterando o arquivo `pom.xml` e `persistence.xml` facilmente se consegue configurar para funcionar com qualquer outro banco
dados, seja ele em memória ou persistente.

<hr>
<h2> Setup do Projeto ? </h2>

1. Clone o projeto para sua máquina local;
2. Após isso faça a importação do projeto para seu compilador usando Maven (Usei o Eclipse para codar);
3. Configure o `pom.xml` e `persistence.xml` de acordo com o seu BD utilizado ; e
4. Deve esta pronto para testar.

<hr>
<h2> Como testar ? </h2>

1. A classe de `MainTeste` possui um método chamado `popularBancoDeDados()` que contém mocks prontos, cujo o objetivo é facilitar o testes;
2. É possível acrescentar novos mocks, seguindo o padrão do método `popularBancoDeDados()`;
3. Execute a classe de testes;
4. No método de main é passada todas a instruções para o usuário seguir com os testes;
5. Existe uma simulação de autenticação cujo os usuários e senhas foram definidos na instância e persistência das Classes `Cliente` e `Funcionário`,
seguem abaixo a tabela: 

| usuario | senha | Classe | - | usuario | senha | Classe |
| :----: | :---: | :-----: | - | :----: | :---: | :-----: |
| gutoyago | testejava | Cliente | - | yayaguedes | testejava | Cliente |
| erikcarolis | testejava | Cliente | - | zecarlos12 | testejava | Cliente |
| dudacamile | testejava | Cliente | - | eliomar1 | testejava | Funcionario |
| marimilene | testejava | Cliente | - | fabianovaz | testejava | Funcionario |
| vallins | testejava | Cliente | - | julymota | testejava | Funcionario |

6. Você pode mockar qualquer atributo que quiser cadastrando manualmente ou inserindo a instância e persistência no método `popularBancoDeDados()`.
7. bons testes.




