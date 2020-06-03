package com.wedding.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wedding.models.Lobby;
import com.wedding.models.TypeLobby;
import com.wedding.service.LobbyService;
import com.wedding.service.TypeLobbyService;
import com.wedding.serviceImpl.LobbyServiceImpl;
import com.wedding.serviceImpl.TypeLobbyServiceImpl;
import com.wedding.utils.PathConstant;
import com.wedding.utils.UrlConstant;

@WebServlet(UrlConstant.URL_LOBBY)
public class LobbyController extends HttpServlet {

	private LobbyService lobbyService;
	private TypeLobbyService typeLobbyService;
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		lobbyService = new LobbyServiceImpl();
		typeLobbyService = new TypeLobbyServiceImpl();
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession userSession = req.getSession();
		String role = userSession.getAttribute("USER_ROLE").toString();
		req.setAttribute("userRole", role);
		
		String username = userSession.getAttribute("LOGIN_USER").toString();
		req.setAttribute("username", username);
		
		
		List<Lobby> lobbies = lobbyService.getAllLobby();
		req.setAttribute("lobbies", lobbies);
		
		List<TypeLobby> lobbyTypes = typeLobbyService.getAllTypeLobby();
		req.setAttribute("lobbyTypes", lobbyTypes);
		
		
		req.getRequestDispatcher(PathConstant.Path_VIEWS + "lobby.jsp").forward(req, resp);	
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		int lobbyTypeID = Integer.parseInt(req.getParameter("lobbyTypeID"));
		String lobbyName = req.getParameter("lobbyName").toString();
		int maxTable = Integer.parseInt(req.getParameter("maxTable"));
		Lobby lobby = new Lobby();
		lobby.setLobbyName(lobbyName);
		lobby.setLobbyTypeID(lobbyTypeID);
		lobby.setMaxTable(maxTable);
		lobbyService.addLobby(lobby);
		resp.sendRedirect(req.getContextPath() + "/lobby");
	}
}
