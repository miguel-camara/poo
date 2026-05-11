package org.miguel.poo.interfacees.repositorios;

import java.util.List;

public interface OrdenableRepositorio<T> {
  List<T> listar(String campo, Direccion dir);
}
