package org.miguel.poo.interfacees.repositorios;

import java.util.List;

public interface PaginableRepositorio<T> {
  List<T> listar(int desde, int hasta);
}
