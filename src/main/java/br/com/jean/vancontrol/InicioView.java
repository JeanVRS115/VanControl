package br.com.jean.vancontrol;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.router.Route;

@Route("")
public class InicioView extends com.vaadin.flow.component.orderedlayout.VerticalLayout {

    public InicioView() {
        H1 titulo = new H1("VanControl");
        Paragraph descricao = new Paragraph(
                "Sistema de gestão de transporte escolar"
        );

        add(titulo, descricao);

        Button botao = new Button("Clique aqui");

        botao.addClickListener(event -> {
            Notification.show("VanControl iniciado com sucesso!");
        });

        add(botao);
    }
}