# Aplicativo para conteúdos de Matemática Elementar

## Motivos para criação
Percebemos que para cada conteúdo de *Matemática Elementar*, casos quiséssemos um aplicativo para conferir o resultado do cálculo era necessário instalar um diferente para cada conteúdo,  tendo assim que ficar alternando entre vários *apps*. 
 
 Poucos dos *apps* que utilizamos, continha um resumo da teoria para eventuais consultas, o que facilitaria caso tivesse dúvidas em algo específico. Junto a isso, quase nenhum mostrava o resultado passo a passo e decidimos utilizar nossos conhecimentos adquiridos em Desenvolvimento Android, para criar um *app* que facilitasse o aprendizado incluindo resolução detalhada e histórico de cálculos.

## Objetivos
- Auxiliar na correção do cálculo
- Facilitar o entendimento da teoria


# Tela de Menu
![](http://i.imgur.com/8Skxcf9.png)
> Na interface, utilizamos um [template de CardView
](https://github.com/eddydn/AndroidGridLayout)


## 1. Tela de Análise Combinatória
![enter image description here](http://imgur.com/k7MOiBel.png)


> - É composta por **4 abas** (Arranjo, Combinação, Permutação e Anagrama), e cada uma possui a fórmula e a resolução detalhada (exceto *permutação*)
> 
> - É gerado o o resultado final  na mesma tela, e caso o usuário queira o detalhamento, deverá arrastar a aba **Passo a Passo** para o topo (há uma [Lottie Animation](https://airbnb.design/lottie/) indicando com uma seta)
>
> - Os trechos que envolvem partes matemáticas, utilizamos a biblioteca  [MathView](https://github.com/jianzhongli/MathView) que a partir dela consegue-se escrever códigos na linguagem [LaTeX](https://www.latex-project.org/)


## 1.2 Aba de Arranjo com passo a passo
![](http://i.imgur.com/ce6MqOE.png)

> Para voltar a tela de Arranjo, o usuário deverá arrastar a área branca para baixo (utilizamos também uma [Lottie Animation](https://airbnb.design/lottie/) para facilitar a compreensão)
