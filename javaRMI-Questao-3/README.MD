## Simples Sistema de Troca de Mensagens feito em javaRMI

| Desenvolvedor | Email | Github |
|-------------------|-----------------------------|-------------------------|
| Kleber de Brito | kleberbritomoreira10@gmail.com | [kleberbritomoreira10](https://github.com/kleberbritomoreira10)
| Lucas S. Souza | lucas.soaresouza@gmail.com | [lucassoaresouza](https://github.com/lucassoaresouza) |

### Para a execução


1 - Navegue até a pasta raiz do programa e compile-o manualmente:  
```

$ javac -d CAMINHO_DO_DIRETORIO UsersAndMessages.java Server.java Client.java  

```
Se executado corretamente este comando não gerará retorno algum



2 - Rode o rmiregisty na porta 8080:
```

$ rmiregistry 8080

```
Este comando, se executado corretamente, não gerará retorno.



3 - Abra mais uma janela do console ainda na raiz do programa e rode o Servidor
```

$ java -classpath CAMINHO_DO_DIRETORIO -Djava.rmi.server.codebase=file:CAMINHO_DO_DIRETORIO Server

```
Se executado corretamente este comando retornará "Servidor pronto!"



4 - Agora basta executar o client em qualquer outra aba do console
```

$ java -classpath CAMINHO_DO_DIRETORIO Client

```
Ao executar este comando você ja poderá interagir com o Servidor via Cliente
