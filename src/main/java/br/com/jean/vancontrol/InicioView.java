package br.com.jean.vancontrol;

import br.com.jean.vancontrol.view.AlunoView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;

@Route("")
@PageTitle("Início | VanControl")
@PermitAll
public class InicioView extends VerticalLayout {

    private final transient AuthenticationContext authContext;

    public InicioView(AuthenticationContext authContext) {
        this.authContext = authContext;

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();

        RouterLink gerenciarAlunos =
                new RouterLink("Gerenciar alunos", AlunoView.class);

        Button sair = new Button("Sair", evento -> this.authContext.logout());

        add(
                new H1("VanControl"),
                new Paragraph("Você entrou no sistema com sucesso."),
                gerenciarAlunos,
                sair
        );
    }
}