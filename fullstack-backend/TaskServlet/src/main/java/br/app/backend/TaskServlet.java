package br.app.backend;

import java.io.IOException;
import java.util.List;

import br.app.wil.backend.service.EmailNotificacaoService;
import br.app.wil.backend.service.NotificacaoService;
import br.app.wil.backend.service.TaskService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TaskService taskService;

	@Override
	public void init() throws ServletException {
		NotificacaoService notificacaoService = new EmailNotificacaoService();
		this.taskService = new TaskService(notificacaoService);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response = taskService.listar(response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response = taskService.adicionar(request.getParameterValues("task"),response);

	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response = taskService.atualizar(request.getParameter("index"), request.getParameter("task"),response);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response = taskService.remover(request.getParameter("index"),response);

	}
}
