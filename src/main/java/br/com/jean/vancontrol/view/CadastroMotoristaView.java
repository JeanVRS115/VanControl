package br.com.jean.vancontrol.view;

import br.com.jean.vancontrol.service.MotoristaService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("cadastro")
@PageTitle("Cadastro de motorista | VanControl")
@AnonymousAllowed
public class CadastroMotoristaView extends VerticalLayout {

    private final MotoristaService motoristaService;

    private final TextField usuario = new TextField("Usuário");
    private final TextField cpf = new TextField("CPF");
    private final PasswordField senha = new PasswordField("Senha");

    public CadastroMotoristaView(MotoristaService motoristaService) {
        this.motoristaService = motoristaService;

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();

        usuario.setRequired(true);
        cpf.setRequired(true);
        senha.setRequired(true);

        Button cadastrar = new Button("Cadastrar", event -> cadastrarMotorista());

        add(
                new H1("Cadastro de motorista"),
                usuario,
                cpf,
                senha,
                cadastrar
        );
    }

    private void cadastrarMotorista() {
        try {
            motoristaService.cadastrar(
                    usuario.getValue(),
                    cpf.getValue(),
                    senha.getValue()
            );

            Notification.show("Cadastro realizado com sucesso.");
            UI.getCurrent().navigate("login");

        } catch (IllegalArgumentException erro) {
            Notification.show(erro.getMessage());
        }
    }
}