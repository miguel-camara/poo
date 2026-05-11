package org.miguel.poo.interfacees.repositorios;

public interface OrdenablePaginableCrudRepositorio<T> extends OrdenableRepositorio<T>,
    PaginableRepositorio<T>, CrudRepositorio<T>, ContableRepositorio {
}
