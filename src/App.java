import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.miguel.poo.abstraccion.forms.ElementoForm;
import org.miguel.poo.abstraccion.forms.InputForm;
import org.miguel.poo.abstraccion.forms.SelectForm;
import org.miguel.poo.abstraccion.forms.TextareaForm;
import org.miguel.poo.abstraccion.forms.select.Opcion;
import org.miguel.poo.abstraccion.forms.validador.*;
import org.miguel.poo.appfacturas.modelo.*;
import org.miguel.poo.interfacees.modelos.ClientePremium;
import org.miguel.poo.interfacees.repositorios.*;
import org.miguel.poo.interfacees.repositorios.lista.ClienteListRepositorio;
import org.miguel.poo.interfacees.repositorios.lista.ProductoListRepositorio;
import org.miguel.poo.sobrecarga.Calculadora;

public class App {
  public static void main(String[] args) throws Exception {
    // factura();
    interfaces();
    // abstraccion();
    // sobrecarga();
    // genericos();
    // genericos2();

  }

  public static void factura() {
    Cliente cliente = new Cliente();
    cliente.setNif("12345-6543");
    cliente.setNombre("Miguel");

    Scanner s = new Scanner(System.in);
    System.out.print("Ingrese una descripción de la factura: ");
    String desc = s.nextLine();
    Factura factura = new Factura(desc, cliente);

    Producto producto;

    System.out.println();

    for (int i = 0; i < 5; i++) {
      producto = new Producto();
      System.out.print("Ingrese producto nº " + producto.getCodigo() + ": ");
      producto.setNombre(s.nextLine());

      System.out.print("Ingrese el precio: ");
      producto.setPrecio(s.nextFloat());

      System.out.print("Ingrese la cantidad: ");
      factura.addItemFactura(new ItemFactura(s.nextInt(), producto));

      System.out.println();
      s.nextLine();
    }
    System.out.println(factura);
  }

  public static void interfaces() {

    OrdenablePaginableCrudRepositorio<org.miguel.poo.interfacees.modelos.Cliente> repo = new ClienteListRepositorio();

    repo.crear(new org.miguel.poo.interfacees.modelos.Cliente("Jano", "Pérez"));
    repo.crear(new org.miguel.poo.interfacees.modelos.Cliente("Bea", "González"));
    repo.crear(new org.miguel.poo.interfacees.modelos.Cliente("Luci", "Martínez"));
    repo.crear(new org.miguel.poo.interfacees.modelos.Cliente("Andrés", "Guzmán"));

    List<org.miguel.poo.interfacees.modelos.Cliente> clientes = repo.listar();
    clientes.forEach(System.out::println);
    System.out.println("===== paginable =====");
    List<org.miguel.poo.interfacees.modelos.Cliente> paginable = repo.listar(1, 4);
    paginable.forEach(System.out::println);

    System.out.println("===== ordenar =====");
    List<org.miguel.poo.interfacees.modelos.Cliente> clientesOrdenAsc = repo.listar("descripcion", Direccion.ASC);
    for (org.miguel.poo.interfacees.modelos.Cliente c : clientesOrdenAsc) {
      System.out.println(c);
    }

    System.out.println("===== editar =====");
    org.miguel.poo.interfacees.modelos.Cliente beaActualizar = new org.miguel.poo.interfacees.modelos.Cliente("Bea",
        "Mena");
    beaActualizar.setId(2);
    repo.editar(beaActualizar);
    System.out.println(" ============= ");
    repo.listar("nombre", Direccion.ASC).forEach(System.out::println);
    System.out.println("===== eliminar ======");
    repo.eliminar(2);
    repo.listar().forEach(System.out::println);
    System.out.println("===== total ===== ");
    System.out.println("Total registros: " + repo.total());

  }

  public static void abstraccion() {
    InputForm username = new InputForm("username");
    username.addValidador(new RequeridoValidador());

    InputForm password = new InputForm("clave", "password");
    password.addValidador(new RequeridoValidador())
        .addValidador(new LargoValidador(6, 12));

    InputForm email = new InputForm("email", "email");
    email.addValidador(new RequeridoValidador())
        .addValidador(new EmailValidador());

    InputForm edad = new InputForm("edad", "number");
    edad.addValidador(new NumeroValidador());

    TextareaForm experiencia = new TextareaForm("exp", 5, 9);

    SelectForm lenguaje = new SelectForm("lenguaje");
    lenguaje.addValidador(new NoNuloValidador());

    lenguaje.addOpcion(new Opcion("1", "Java").setSelected())
        .addOpcion(new Opcion("2", "Python"))
        .addOpcion(new Opcion("3", "JavaScript"))
        .addOpcion(new Opcion("4", "TypeScript"))
        .addOpcion(new Opcion("5", "PHP"));

    ElementoForm saludar = new ElementoForm("saludo") {
      @Override
      public String dibujarHtml() {
        return "<input disabled name='" + this.nombre + "' value=\"" + this.valor + "\">";
      }
    };

    saludar.setValor("Hola que tal este campo está deshabilitado!");
    username.setValor("john.doe");
    password.setValor("a1b2c3");
    email.setValor("john.doe@correo.com");
    edad.setValor("28");
    experiencia.setValor("... más de 10 años de experiencia ...");

    List<ElementoForm> elementos = Arrays.asList(username,
        password,
        email,
        edad,
        experiencia,
        lenguaje,
        saludar);

    elementos.forEach(e -> {
      System.out.println(e.dibujarHtml());
      System.out.println("<br>");
    });

    elementos.forEach(e -> {
      if (!e.esValido()) {
        e.getErrores().forEach(System.out::println);
      }
    });
  }

  public static void sobrecarga() {
    System.out.println("sumar int : " + Calculadora.sumar(10, 5));
    System.out.println("sumar float: " + Calculadora.sumar(10.0F, 5F));
    System.out.println("sumar float-int: " + Calculadora.sumar(10f, 5));
    System.out.println("sumar int-float: " + Calculadora.sumar(10, 5.0F));
    System.out.println("sumar double: " + Calculadora.sumar(10.0, 5.0));
    System.out.println("sumar string: " + Calculadora.sumar("10", "5"));
    System.out.println("sumar tres int: " + Calculadora.sumar(10, 5, 3));
    System.out.println("sumar 4 int: " + Calculadora.sumar(10, 5, 3, 4));
    System.out.println("sumar 6 int: " + Calculadora.sumar(10, 5, 3, 4, 6, 7));
    System.out.println("sumar float + n int: " + Calculadora.sumar(10.5F, 5, 9, 15));
    System.out.println("sumar 4 double: " + Calculadora.sumar(10.0, 5.0, 3.5, 4.5));

    System.out.println("sumar long: " + Calculadora.sumar(10L, 5L));
    System.out.println("sumar int: " + Calculadora.sumar(10, '@'));
    System.out.println("sumar float-int: " + Calculadora.sumar(10F, '@'));
  }

  public static void genericos() {
    List<org.miguel.poo.interfacees.modelos.Cliente> clientes = new ArrayList<>();
    clientes.add(new org.miguel.poo.interfacees.modelos.Cliente("Andres", "Guzmán"));

    org.miguel.poo.interfacees.modelos.Cliente andres = clientes.iterator().next();

    org.miguel.poo.interfacees.modelos.Cliente[] clientesArreglo = {
        new org.miguel.poo.interfacees.modelos.Cliente("Luci", "Martínez"),
        new org.miguel.poo.interfacees.modelos.Cliente("Andres", "Guzmán") };
    Integer[] enterosArreglo = { 1, 2, 3 };

    List<org.miguel.poo.interfacees.modelos.Cliente> clientesLista = fromArrayToList(clientesArreglo);
    List<Integer> enterosLista = fromArrayToList(enterosArreglo);

    clientesLista.forEach(System.out::println);
    enterosLista.forEach(System.out::println);

    List<String> nombres = fromArrayToList(new String[] { "Andrés", "Pepe",
        "Luci", "Bea", "John" }, enterosArreglo);
    nombres.forEach(System.out::println);

    List<ClientePremium> clientesPremiumList = fromArrayToList(
        new ClientePremium[] { new ClientePremium("Paco", "Fernández") });

    imprimirClientes(clientes);
    imprimirClientes(clientesLista);
    imprimirClientes(clientesPremiumList);

    System.out.println("Máximo de 1, 9 y 4 es: " + maximo(1, 9, 4));
    System.out.println("Máximo de 3.9, 11.6, 7.78 es: " + maximo(3.9, 11.6, 7.78));
    System.out.println("Máximo de zanahoria, arándanos, manzana es: "
        + maximo("zanahoria", "arándano", "manzana"));

  }

  public static <T> List<T> fromArrayToList(T[] c) {
    return Arrays.asList(c);
  }

  public static <T extends Number> List<T> fromArrayToList(T[] c) {
    return Arrays.asList(c);
  }

  public static <T extends Cliente & Comparable<T>> List<T> fromArrayToList(T[] c) {
    return Arrays.asList(c);
  }

  public static <T, G> List<T> fromArrayToList(T[] c, G[] x) {
    for (G elemento : x) {
      System.out.println(elemento);
    }
    return Arrays.asList(c);
  }

  public static void imprimirClientes(List<? extends org.miguel.poo.interfacees.modelos.Cliente> clientes) {
    clientes.forEach(System.out::println);
  }

  public static <T extends Comparable<T>> T maximo(T a, T b, T c) {
    T max = a;
    if (b.compareTo(max) > 0) {
      max = b;
    }
    if (c.compareTo(max) > 0) {
      max = c;
    }
    return max;
  }

  public static void genericos2() {
    OrdenablePaginableCrudRepositorio<org.miguel.poo.interfacees.modelos.Producto> repo = new ProductoListRepositorio();
    repo.crear(new org.miguel.poo.interfacees.modelos.Producto("mesa", 50.52));
    repo.crear(new org.miguel.poo.interfacees.modelos.Producto("silla", 18));
    repo.crear(new org.miguel.poo.interfacees.modelos.Producto("lampara", 15.5));
    repo.crear(new org.miguel.poo.interfacees.modelos.Producto("notebook", 400.89));

    List<org.miguel.poo.interfacees.modelos.Producto> productos = repo.listar();
    productos.forEach(System.out::println);
    System.out.println("===== paginable =====");
    List<org.miguel.poo.interfacees.modelos.Producto> paginable = repo.listar(1, 4);
    paginable.forEach(System.out::println);

    System.out.println("===== ordenar =====");
    List<org.miguel.poo.interfacees.modelos.Producto> productosOrdenAsc = repo.listar("descripcion", Direccion.ASC);
    for (org.miguel.poo.interfacees.modelos.Producto c : productosOrdenAsc) {
      System.out.println(c);
    }

    System.out.println("===== editar =====");
    org.miguel.poo.interfacees.modelos.Producto lamparaActualizar = new org.miguel.poo.interfacees.modelos.Producto(
        "lampara escritorio", 23);
    lamparaActualizar.setId(3);
    repo.editar(lamparaActualizar);
    org.miguel.poo.interfacees.modelos.Producto lampara = repo.porId(3);
    System.out.println(lampara);
    System.out.println(" ============= ");
    repo.listar("precio", Direccion.ASC).forEach(System.out::println);
    System.out.println("===== eliminar ======");
    repo.eliminar(2);
    repo.listar().forEach(System.out::println);
    System.out.println("===== total ===== ");
    System.out.println("Total registros: " + repo.total());
  }

}
