package org.miguel.poo.interfacees.repositorios;

import java.util.List;

import org.miguel.poo.interfacees.modelos.Cliente;

public interface PaginableRepositorio {
  List<Cliente> listar(int desde, int hasta);
}
