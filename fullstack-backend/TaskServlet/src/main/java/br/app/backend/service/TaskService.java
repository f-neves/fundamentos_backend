package br.app.backend.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

public class TaskManagerService {
    private List<String> taskList;
    private NotificacaoService notificationService;

    public TaskManagerService(NotificacaoService notificationService) {
        this.taskList = new ArrayList<>();
        this.notificationService = notificationService;
    }

    public HttpServletResponse getAllTasks(HttpServletResponse response) {
        try {
            if (taskList.isEmpty()) {
                response.getWriter().print("Nenhuma tarefa disponível!");
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                notificationService.enviarNotificacao("Tentativa de visualizar tarefas - Nenhuma tarefa disponível!", "usuario@email.com");
            } else {
                for (int i = 0; i < taskList.size(); i++) {
                    response.getWriter().println((i + 1) + ". " + taskList.get(i));
                }
                response.setStatus(HttpServletResponse.SC_OK);
                notificationService.enviarNotificacao("Tarefas listadas com sucesso", "usuario@email.com");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public HttpServletResponse addNewTasks(String[] newTasks, HttpServletResponse response) throws IOException {
        StringBuilder addedTasks = new StringBuilder();
        if (newTasks != null && newTasks.length > 0) {
            for (String task : newTasks) {
                if (task != null && !task.trim().isEmpty()) {
                    taskList.add(task);
                    addedTasks.append(task).append(", ");
                }
            }
            notificationService.enviarNotificacao("Novas tarefas foram adicionadas: " + addedTasks, "usuario@email.com");
            response.getWriter().print("Novas tarefas foram adicionadas: " + addedTasks);
            response.setStatus(HttpServletResponse.SC_CREATED);
            return response;
        }
        response.getWriter().print("Tarefa recebida é inválida!");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        notificationService.enviarNotificacao("Erro ao adicionar: Tarefa recebida é inválida!", "usuario@email.com");
        return response;
    }

    public HttpServletResponse modifyTask(String indexStr, String updatedTask, HttpServletResponse response) throws IOException {
        try {
            int index = Integer.parseInt(indexStr);
            if (updatedTask != null && !updatedTask.trim().isEmpty()) {
                if (index >= 0 && index < taskList.size()) {
                    taskList.set(index, updatedTask);
                    notificationService.enviarNotificacao("Tarefa foi atualizada: " + updatedTask, "usuario@email.com");
                    response.getWriter().print("Tarefa foi atualizada: " + updatedTask);
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    notificationService.enviarNotificacao("Falha ao atualizar tarefa: Tarefa não encontrada no índice " + index, "usuario@email.com");
                    response.getWriter().print("Falha ao atualizar tarefa: Tarefa não encontrada!");
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                notificationService.enviarNotificacao("Falha ao atualizar tarefa: Tarefa inválida", "usuario@email.com");
                response.getWriter().print("Falha ao atualizar tarefa: Tarefa inválida");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            response.getWriter().print("O índice fornecido não é numérico!");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            notificationService.enviarNotificacao("Erro ao atualizar tarefa: Índice não é numérico", "usuario@email.com");
        } catch (IOException e) {
            response.getWriter().print("Erro ao atualizar tarefa!");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            notificationService.enviarNotificacao("Erro ao atualizar tarefa! " + e.getMessage(), "usuario@email.com");
        }
        return response;
    }

    public HttpServletResponse deleteTask(String indexStr, HttpServletResponse response) throws IOException {
        try {
            int index = Integer.parseInt(indexStr);
            if (index >= 0 && index < taskList.size()) {
                String removedTask = taskList.remove(index);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().print("Tarefa removida: " + removedTask);
                notificationService.enviarNotificacao("Tarefa removida: " + removedTask, "usuario@email.com");
            } else {
                response.getWriter().print("A tarefa não foi encontrada!");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                notificationService.enviarNotificacao("Erro ao remover tarefa: Índice inválido", "usuario@email.com");
            }
        } catch (NumberFormatException e) {
            response.getWriter().print("O índice fornecido não é numérico!");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            notificationService.enviarNotificacao("Erro ao remover tarefa: Índice não é numérico", "usuario@email.com");
        } catch (IOException e) {
            response.getWriter().print("Erro ao remover tarefa!");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            notificationService.enviarNotificacao("Erro ao remover tarefa! " + e.getMessage(), "usuario@email.com");
        }
        return response;
    }
}
