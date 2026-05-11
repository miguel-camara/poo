package org.miguel.poo.interfacees.repositorios;



import java.util.List;

import org.miguel.poo.interfacees.modelos.Cliente;

public interface CrudRepositorio {
  List<Cliente> listar();

  Cliente porId(Integer id);

  void crear(Cliente cliente);

  void editar(Cliente cliente);

  void eliminar(Integer id);
}
