package br.app.backend.service;

public class EmailNotificacaoService implements NotificacaoService {

    @Override
    public void enviarNotificacao(String mensagem, String destinatario) {
        System.out.println("Mensagem de e-mail para " + destinatario + " : " + mensagem);
    }
}
