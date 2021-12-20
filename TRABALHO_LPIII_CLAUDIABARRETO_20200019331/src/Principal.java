import Melhorias.*;
import processing.core.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Principal extends PApplet {

    public static void main(String args[]) {
        PApplet.main("Principal");
    }

    PImage imgMenu;
    PImage imgInstrucoes;
    PImage imgFundo;
    PImage imgEndgame;

    enum STATUS { NO_MENU, TUTORIAL, EM_JOGO, ENDGAME };
    STATUS status = STATUS.NO_MENU;
    Map<String, PImage> imagens = new HashMap<>();

    private int quantidadeExploits = 0;
    private List<Melhoria> listaMelhorias = new ArrayList<>();
    DecimalFormat formatar = new DecimalFormat("0");

    private int contagemQuadros = 0; //considerando que cada segundo tem 60 draw()

    @Override
    public void settings() {
        size(1280, 720);
    }

    @Override
    public void setup() {
        imgMenu = loadImage("imagens/hackercats.png");
        imgInstrucoes = loadImage("imagens/instrucoes.png");
        imgFundo = loadImage("imagens/gatinhacker.png");
        imgEndgame = loadImage("imagens/fim.png");

        listaMelhorias.add(new Arduino());
        listaMelhorias.add(new RaspberryPI());
        listaMelhorias.add(new Rubberducky());
        listaMelhorias.add(new PCcomKALILINUX());
        listaMelhorias.add(new BotNet());
        listaMelhorias.add(new ExploitarEstado());

        for(Melhoria i : listaMelhorias){
            imagens.put(i.getDescricao(), loadImage("imagens/" + i.getFoto()));
        }
    }

    @Override
    public void draw() {
        if(contagemQuadros == 60){
            adicionaExploits();
            contagemQuadros = 1;
        } else { contagemQuadros += 1; }

        clear();

        if(status == STATUS.NO_MENU){
            image(imgMenu, 0, 0);

            fill(200);

            rect(500, 610, 200, 50, 10,10,10,10);
            textSize(30);

            fill(50);
            text("Jogar", 560, 645);
        } else if(status == STATUS.TUTORIAL) {
            image(imgInstrucoes, 0, 0);

            fill(200);

            rect(500, 610, 200, 50, 10,10,10,10);
            textSize(30);

            fill(50);
            text("Começar", 540, 645);

        } else if(status == STATUS.EM_JOGO) {

            image(imgFundo, 0, 0);

            int x = 130; int y = 60;
            for(Melhoria i : listaMelhorias){
                fill(255);
                rect(x,y, 150, 120, 8,8,8,8);
                fill(0);
                textSize(16);
                text(i.getDescricao() + " (" + i.getQuantidade() + ")", x + 5, y + 20);

                image(imagens.get(i.getDescricao()), x + 44, y + 35);

                text("Custo: " + formatar.format(i.getCustoCompra() + i.getQuantidade() * i.getCustoCompra() * 0.1), x + 35, y + 110);
                x += 160;
            }

            fill(255);
            textSize(20);
            text("Quantidade Exploits: " + quantidadeExploits, 150, 345);

        } else {
            image(imgEndgame, 0, 0);
        }

    }

    @Override
    public void mousePressed() {
        if(status == STATUS.NO_MENU) {
            if (mouseX >= 500 && mouseX <= 700 && mouseY >= 610 && mouseY <= 660) {
                // MUDA TELA
                status = STATUS.TUTORIAL;
            }
        } else if(status == STATUS.TUTORIAL) {
            if (mouseX >= 500 && mouseX <= 700 && mouseY >= 610 && mouseY <= 660) {
                // MUDA TELA
                status = STATUS.EM_JOGO;
            }
        } else {
            //CONTABILIZA CLICKS
            int x = 130; int y = 100;
            for(Melhoria i : listaMelhorias){
                if(mouseX >= x && mouseX <= x + 150 && mouseY >= y && mouseY <= y + 120 ){
                    if(quantidadeExploits >= (i.getCustoCompra() + i.getQuantidade() * 1.1)){
                        i.compra();
                        quantidadeExploits -= (i.getCustoCompra() + i.getQuantidade() * 1.1);
                        if(i.getDescricao() == "Exploit ESTADO") {
                            status = STATUS.ENDGAME;
                        }
                    }
                }
                x += 160;
            }
            quantidadeExploits += 1;
        }
    }

    private void adicionaExploits(){
        for(Melhoria i : listaMelhorias){
            quantidadeExploits += i.getQuantidade() * i.getExploitsPorSegundo();
        }
    }

}
