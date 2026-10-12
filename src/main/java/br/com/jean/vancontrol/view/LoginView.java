package br.com.jean.vancontrol.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Login | VanControl")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm loginForm = new LoginForm();

    public LoginView() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();

        LoginI18n mensagens = LoginI18n.createDefault();
        mensagens.getForm().setTitle("Acessar VanControl");
        mensagens.getForm().setUsername("CPF");
        mensagens.getForm().setPassword("Senha");
        mensagens.getForm().setSubmit("Entrar");
        mensagens.getErrorMessage().setTitle("Não foi possível entrar");
        mensagens.getErrorMessage().setMessage("CPF ou senha inválidos.");

        loginForm.setI18n(mensagens);
        loginForm.setAction("login");
        loginForm.setForgotPasswordButtonVisible(false);

        RouterLink irParaCadastro = new RouterLink(
                "Ainda não possui cadastro? Cadastre-se",
                CadastroMotoristaView.class
        );

        add(
                new H1("VanControl"),
                loginForm,
                irParaCadastro
        );
    }

    @Override
    public void beforeEnter(BeforeEnterEvent evento) {
        boolean houveErro = evento.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error");

        loginForm.setError(houveErro);
    }
}