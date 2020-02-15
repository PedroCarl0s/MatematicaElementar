
# ﻿Aplicativo para conteúdos de Matemática Elementar [(download)](http://laudelino.dce.ufpb.br/2-projeto-gabarito/matematicaelementar%20v1.apk?attredirects=0&d=1)
﻿
## Motivos para criação
Percebemos que para cada conteúdo de *Matemática Elementar*, casos quiséssemos um aplicativo para conferir o resultado do cálculo era necessário instalar um diferente para cada conteúdo,  tendo assim que ficar alternando entre vários *apps*. 
 
 Poucos dos *apps* que utilizamos, continha um resumo da teoria para eventuais consultas, o que facilitaria caso tivesse dúvidas em algo específico. Junto a isso, quase nenhum mostrava o resultado passo a passo e decidimos utilizar nossos conhecimentos adquiridos em Desenvolvimento Android, para criar um *app* que facilitasse o aprendizado incluindo resolução detalhada e histórico de cálculos.

## Objetivos
- Auxiliar na correção do cálculo
- Facilitar o entendimento da teoria


# Tela de Menu
![](https://imgur.com/yY9OJ76.png)
> Na interface, utilizamos um [template de CardView
](https://github.com/eddydn/AndroidGridLayout)


## 1. Tela de Análise Combinatória

### 1.1 Aba de Permutação (c/ passo a passo)
![enter image description here](https://imgur.com/9D6bTRw.gif)

---

### 1.2 Aba de Combinação (s/ passo a passo)
![enter image description here](https://imgur.com/VpSkgl2.gif)


> **OBS.:** O cálculo com passo a passo está sendo finalizado

---

### 1.3 Aba de Fatorial (s/ passo a passo)
![enter image description here](https://imgur.com/0LkIuFA.gif)

---

### 1.4 Aba de Anagrama (c/ passo a passo)
![enter image description here](https://imgur.com/c8xW3VA.gif)


> **OBS.:** Futuramente haverá uma versão com cálculos mais detalhados



> - É gerado o o resultado final  na mesma tela, e caso o usuário queira o detalhamento, deverá arrastar a aba **Passo a Passo** para o topo (há uma [Lottie Animation](https://airbnb.design/lottie/) indicando com uma seta)
>
> - Os trechos que envolvem partes matemáticas, utilizamos a biblioteca  [MathView](https://github.com/jianzhongli/MathView) que a partir dela consegue-se escrever códigos na linguagem [LaTeX](https://www.latex-project.org/)


## 2. Tela de Operações entre conjuntos (União, Interseção, Diferença (A-B), Diferença (B-A))

![enter image description here](https://imgur.com/7bGJB1P.gif)

> **OBS.:** Futuramente haverá as opções: complementar e diferença simétrica


## 3. Tela de Sobre

![enter image description here](https://imgur.com/y6m056h.gif)

> **OBS.:** Nesta tela, utilizamos um [template de About Info
](https://github.com/fennifith/Attribouter)



## 4. Atualizações
Ao decorrer do ano de 2020, novas funcionalidades serão adicionadas, correção de bugs de visualização em alguns tamanhos de tela e criação de testes unitários. 
