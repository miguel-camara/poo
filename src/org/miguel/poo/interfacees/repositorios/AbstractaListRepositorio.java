package org.miguel.poo.interfacees.repositorios;

import java.util.ArrayList;
import java.util.List;

import org.miguel.poo.excepciones.EscrituraAccessDatoException;
import org.miguel.poo.excepciones.LecturaAccesoDatoException;
import org.miguel.poo.interfacees.modelos.BaseEntity;

public abstract class AbstractaListRepositorio<T extends BaseEntity> implements OrdenablePaginableCrudRepositorio<T> {

  protected List<T> dataSource;

  public AbstractaListRepositorio() {
    this.dataSource = new ArrayList<T>();
  }

  @Override
  public List<T> listar() {
    return dataSource;
  }

  @Override
  public T porId(Integer id) throws LecturaAccesoDatoException {
    if (id == null || id <= 0) {
      throw new LecturaAccesoDatoException("El ID ingresado es invalido");
    }

    T resultado = null;
    for (T cli : dataSource) {
      if (cli.getId() != null && cli.getId().equals(id)) {
        resultado = cli;
        break;
      }
    }

    if (resultado == null) {
      throw new LecturaAccesoDatoException(String.format("Error al editar el objeto con id = %d", id));
    }

    return resultado;
  }

  @Override
  public void crear(T t) throws EscrituraAccessDatoException {
    if (t == null)
      throw new EscrituraAccessDatoException("No su puede crear un objeto nulo");

    this.dataSource.add(t);

  }

  @Override
  public void eliminar(Integer id) throws LecturaAccesoDatoException {
    this.dataSource.remove(this.porId(id));
  }

  @Override
  public List<T> listar(int desde, int hasta) {
    return dataSource.subList(desde, hasta);
  }

  @Override
  public int total() {
    return this.dataSource.size();
  }
}
