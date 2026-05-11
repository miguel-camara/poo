package org.miguel.poo.interfacees.repositorios;

import org.miguel.poo.interfacees.modelos.Cliente;

import java.util.List;

public interface OrdenableRepositorio {
  List<Cliente> listar(String campo, Direccion dir);
}
