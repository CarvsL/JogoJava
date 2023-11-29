
package com.mycompany.jogocerto;

import mongo.Entrada;

public class PaginaStart {

    void setVisible(boolean b) {
        Entrada entrada = new Entrada();
        new Game(entrada);  
    }
}
