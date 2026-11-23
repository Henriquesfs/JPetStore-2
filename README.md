# JPetStore

O Spring JPetStore, loja virtual fictícia distribuída com o Spring Framework
2.5 em 2010, foi atualizado para Java 21 e Spring 7 por meio de refatorações
incrementais, sem alteração no funcionamento da loja. As duas versões ficam no
mesmo repositório para comparação.

## Pastas

| Pasta | Conteúdo |
| --- | --- |
| `jpetstore-2010/` | Código original: Java 1.4, Spring 2.5, iBATIS 2 e JSP, acrescido apenas do build Maven e do Dockerfile |
| `jpetstore-2026/` | Versão refatorada: Java 21, Spring 7, MyBatis 3 e Jakarta EE 11, com 88 testes automatizados |

Em `jpetstore-2026/`, o código Java fica em `src/main/java` (pacotes
`controller`, `service`, `validator`, `dao` e `model`), as páginas e a folha de
estilo em `src/main/webapp`, os testes em `src/test/java` e o banco HSQLDB em
`db/hsqldb`.

## Como executar

Com Docker, a partir da raiz do repositório:

| Versão | Comandos | Endereço |
| --- | --- | --- |
| 2010 | `docker build -t jpetstore-2010 jpetstore-2010`<br>`docker run --rm -p 8081:8080 jpetstore-2010` | <http://localhost:8081/jpetstore/> |
| 2026 | `docker build -t jpetstore-2026 jpetstore-2026`<br>`docker run --rm -p 8082:8080 jpetstore-2026` | <http://localhost:8082/jpetstore/> |

O usuário de demonstração é `j2ee`, com senha `j2ee`, e cada contêiner começa
com o banco original.

Sem Docker, a versão 2026 precisa do JDK 21 e do Maven. Inicie o banco na pasta
`jpetstore-2026/db/hsqldb`:

```powershell
java -cp $HOME\.m2\repository\org\hsqldb\hsqldb\2.7.4\hsqldb-2.7.4.jar org.hsqldb.server.Server
```

Depois, em outro terminal, na pasta `jpetstore-2026`, rode `mvn jetty:run` para
abrir a loja em <http://localhost:8080/jpetstore/> ou `mvn test` para os testes.
