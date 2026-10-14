package br.com.jean.vancontrol.view;

import br.com.jean.vancontrol.model.Aluno;
import br.com.jean.vancontrol.service.AlunoService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.context.SecurityContextHolder;

@Route("alunos")
@PageTitle("Cadastro de alunos | VanControl")
@PermitAll
public class AlunoView extends VerticalLayout {

    private final AlunoService alunoService;

    private final TextField nome = new TextField("Nome do aluno");
    private final TextField cpf = new TextField("CPF do aluno");

    private final TextField nomeResponsavel1 =
            new TextField("Nome do responsável 1");
    private final TextField cpfResponsavel1 =
            new TextField("CPF do responsável 1");

    private final TextField nomeResponsavel2 =
            new TextField("Nome do responsável 2");
    private final TextField cpfResponsavel2 =
            new TextField("CPF do responsável 2");

    private final Button salvar = new Button("Cadastrar aluno");
    private final Grid<Aluno> grade = new Grid<>(Aluno.class, false);

    private Long alunoEmEdicaoId;

    public AlunoView(AlunoService alunoService) {
        this.alunoService = alunoService;

        nome.setRequired(true);
        cpf.setRequired(true);

        salvar.addClickListener(evento -> salvarAluno());

        grade.addColumn(Aluno::getNome).setHeader("Aluno");
        grade.addColumn(Aluno::getCpf).setHeader("CPF");
        grade.addColumn(Aluno::getNomeResponsavel1)
                .setHeader("Responsável 1");
        grade.addColumn(Aluno::getNomeResponsavel2)
                .setHeader("Responsável 2");

        grade.addComponentColumn(aluno -> {
            Button editar = new Button(
                    "Editar",
                    evento -> carregarParaEdicao(aluno)
            );

            Button excluir = new Button(
                    "Excluir",
                    evento -> confirmarExclusao(aluno)
            );

            return new HorizontalLayout(editar, excluir);
        }).setHeader("Ações");

        grade.setWidthFull();

        add(
                new H1("Cadastro de aluno"),
                nome,
                cpf,
                nomeResponsavel1,
                cpfResponsavel1,
                nomeResponsavel2,
                cpfResponsavel2,
                salvar,
                grade
        );

        atualizarLista();
    }

    private void salvarAluno() {
        try {
            if (alunoEmEdicaoId == null) {
                alunoService.cadastrar(
                        cpfMotoristaLogado(),
                        nome.getValue(),
                        cpf.getValue(),
                        nomeResponsavel1.getValue(),
                        cpfResponsavel1.getValue(),
                        nomeResponsavel2.getValue(),
                        cpfResponsavel2.getValue()
                );

                Notification.show("Aluno cadastrado com sucesso.");
            } else {
                alunoService.atualizar(
                        alunoEmEdicaoId,
                        cpfMotoristaLogado(),
                        nome.getValue(),
                        cpf.getValue(),
                        nomeResponsavel1.getValue(),
                        cpfResponsavel1.getValue(),
                        nomeResponsavel2.getValue(),
                        cpfResponsavel2.getValue()
                );

                Notification.show("Aluno atualizado com sucesso.");
            }

            limparFormulario();
            atualizarLista();

        } catch (IllegalArgumentException erro) {
            Notification.show(erro.getMessage());
        }
    }

    private void carregarParaEdicao(Aluno aluno) {
        alunoEmEdicaoId = aluno.getId();

        nome.setValue(aluno.getNome());
        cpf.setValue(aluno.getCpf());
        nomeResponsavel1.setValue(valorOuVazio(aluno.getNomeResponsavel1()));
        cpfResponsavel1.setValue(valorOuVazio(aluno.getCpfResponsavel1()));
        nomeResponsavel2.setValue(valorOuVazio(aluno.getNomeResponsavel2()));
        cpfResponsavel2.setValue(valorOuVazio(aluno.getCpfResponsavel2()));

        salvar.setText("Salvar alterações");
    }

    private void confirmarExclusao(Aluno aluno) {
        ConfirmDialog dialogo = new ConfirmDialog();

        dialogo.setHeader("Excluir aluno?");
        dialogo.setText("Deseja excluir " + aluno.getNome() + "?");
        dialogo.setCancelable(true);
        dialogo.setCancelText("Cancelar");
        dialogo.setConfirmText("Excluir");
        dialogo.addConfirmListener(evento -> excluirAluno(aluno));

        dialogo.open();
    }

    private void excluirAluno(Aluno aluno) {
        try {
            alunoService.excluir(aluno.getId(), cpfMotoristaLogado());

            if (aluno.getId().equals(alunoEmEdicaoId)) {
                limparFormulario();
            }

            Notification.show("Aluno excluído com sucesso.");
            atualizarLista();

        } catch (IllegalArgumentException erro) {
            Notification.show(erro.getMessage());
        }
    }

    private String cpfMotoristaLogado() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    private void atualizarLista() {
        grade.setItems(
                alunoService.listarDoMotorista(cpfMotoristaLogado())
        );
    }

    private void limparFormulario() {
        alunoEmEdicaoId = null;

        nome.clear();
        cpf.clear();
        nomeResponsavel1.clear();
        cpfResponsavel1.clear();
        nomeResponsavel2.clear();
        cpfResponsavel2.clear();

        salvar.setText("Cadastrar aluno");
    }

    private String valorOuVazio(String valor) {
        return valor == null ? "" : valor;
    }
}