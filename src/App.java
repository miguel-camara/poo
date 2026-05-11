import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.miguel.poo.abstraccion.forms.ElementoForm;
import org.miguel.poo.abstraccion.forms.InputForm;
import org.miguel.poo.abstraccion.forms.SelectForm;
import org.miguel.poo.abstraccion.forms.TextareaForm;
import org.miguel.poo.abstraccion.forms.select.Opcion;
import org.miguel.poo.abstraccion.forms.validador.EmailValidador;
import org.miguel.poo.abstraccion.forms.validador.LargoValidador;
import org.miguel.poo.abstraccion.forms.validador.NoNuloValidador;
import org.miguel.poo.abstraccion.forms.validador.NumeroValidador;
import org.miguel.poo.abstraccion.forms.validador.RequeridoValidador;
import org.miguel.poo.appfacturas.modelo.*;
import org.miguel.poo.interfacees.repositorios.*;

public class App {
  public static void main(String[] args) throws Exception {
    // factura();
    // interfaces();
    abstraccion();
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

    CrudRepositorio repo = new ClienteListRepositorio();
    repo.crear(new org.miguel.poo.interfacees.modelos.Cliente("Jano", "Pérez"));
    repo.crear(new org.miguel.poo.interfacees.modelos.Cliente("Bea", "González"));
    repo.crear(new org.miguel.poo.interfacees.modelos.Cliente("Luci", "Martínez"));
    repo.crear(new org.miguel.poo.interfacees.modelos.Cliente("Andrés", "Guzmán"));

    List<org.miguel.poo.interfacees.modelos.Cliente> clientes = repo.listar();
    clientes.forEach(System.out::println);
    System.out.println("===== paginable =====");
    List<org.miguel.poo.interfacees.modelos.Cliente> paginable = ((PaginableRepositorio) repo).listar(1, 4);
    paginable.forEach(System.out::println);

    System.out.println("===== ordenar =====");
    List<org.miguel.poo.interfacees.modelos.Cliente> clientesOrdenAsc = ((OrdenableRepositorio) repo)
        .listar("apellido", Direccion.ASC);
    for (org.miguel.poo.interfacees.modelos.Cliente c : clientesOrdenAsc) {
      System.out.println(c);
    }

    System.out.println("===== editar =====");
    org.miguel.poo.interfacees.modelos.Cliente beaActualizar = new org.miguel.poo.interfacees.modelos.Cliente("Bea",
        "Mena");
    beaActualizar.setId(2);
    repo.editar(beaActualizar);
    org.miguel.poo.interfacees.modelos.Cliente bea = repo.porId(2);
    System.out.println(bea);
    System.out.println(" ============= ");
    ((OrdenableRepositorio) repo)
        .listar("nombre", Direccion.ASC).forEach(System.out::println);
    System.out.println("===== eliminar ======");
    // repo.eliminar(2);
    repo.listar().forEach(System.out::println);

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
}
