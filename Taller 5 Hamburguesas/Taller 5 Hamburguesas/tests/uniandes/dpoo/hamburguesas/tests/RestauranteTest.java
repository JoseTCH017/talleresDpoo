package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.excepciones.HamburguesaException;
import uniandes.dpoo.hamburguesas.excepciones.IngredienteRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.NoHayPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoFaltanteException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.YaHayUnPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.mundo.Restaurante;

public class RestauranteTest
{
    private Restaurante restaurante;
    private File archivoIngredientesValido;
    private File archivoMenuValido;
    private File archivoCombosValido;
    private ArrayList<File> archivosCreados;

    @BeforeEach
    void setUp( ) throws Exception
    {
        restaurante = new Restaurante( );
        archivoIngredientesValido = new File( "data/ingredientes.txt" );
        archivoMenuValido = new File( "data/menu.txt" );
        archivoCombosValido = new File( "data/combos.txt" );
        archivosCreados = new ArrayList<>( );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
        for( File f : archivosCreados )
        {
            if( f != null && f.exists( ) )
            {
                f.delete( );
            }
        }

        // Limpiar posibles facturas generadas en ./facturas/
        File carpetaFacturas = new File( "./facturas/" );
        if( carpetaFacturas.exists( ) && carpetaFacturas.isDirectory( ) )
        {
            File[] facturas = carpetaFacturas.listFiles( );
            if( facturas != null )
            {
                for( File fac : facturas )
                {
                    fac.delete( );
                }
            }
        }
    }

    private File crearArchivoTemporal( String nombre, String contenido ) throws IOException
    {
        File archivo = new File( nombre );
        FileWriter writer = new FileWriter( archivo );
        writer.write( contenido );
        writer.close( );
        archivosCreados.add( archivo );
        return archivo;
    }

    @Test
    void testEstadoInicial( )
    {
        assertNull( restaurante.getPedidoEnCurso( ), "Inicialmente no debe haber pedido en curso." );
        assertTrue( restaurante.getPedidos( ).isEmpty( ), "Inicialmente la lista de pedidos debe estar vacía." );
        assertTrue( restaurante.getIngredientes( ).isEmpty( ), "Inicialmente la lista de ingredientes debe estar vacía." );
        assertTrue( restaurante.getMenuBase( ).isEmpty( ), "Inicialmente la lista de menú base debe estar vacía." );
        assertTrue( restaurante.getMenuCombos( ).isEmpty( ), "Inicialmente la lista de combos debe estar vacía." );
    }

    @Test
    void testIniciarPedidoExitoso( ) throws Exception
    {
        restaurante.iniciarPedido( "Juan Perez", "Calle 1 # 2-3" );
        Pedido enCurso = restaurante.getPedidoEnCurso( );

        assertNotNull( enCurso, "Debe haber un pedido en curso luego de iniciar pedido." );
        assertEquals( "Juan Perez", enCurso.getNombreCliente( ), "El nombre del cliente debe coincidir." );
    }

    @Test
    void testIniciarPedidoLanzaYaHayUnPedidoEnCursoException( ) throws Exception
    {
        restaurante.iniciarPedido( "Juan Perez", "Calle 1 # 2-3" );

        YaHayUnPedidoEnCursoException ex = assertThrows( YaHayUnPedidoEnCursoException.class, ( ) -> {
            restaurante.iniciarPedido( "Maria Gomez", "Carrera 4 # 5-6" );
        }, "Debe lanzar YaHayUnPedidoEnCursoException si ya existe un pedido en curso." );

        assertTrue( ex.getMessage( ).contains( "Juan Perez" ), "El mensaje debe contener el nombre del cliente actual." );
        assertTrue( ex.getMessage( ).contains( "Maria Gomez" ), "El mensaje debe contener el nombre del nuevo cliente." );
    }

    @Test
    void testCerrarYGuardarPedidoSinPedidoEnCurso( )
    {
        NoHayPedidoEnCursoException ex = assertThrows( NoHayPedidoEnCursoException.class, ( ) -> {
            restaurante.cerrarYGuardarPedido( );
        }, "Debe lanzar NoHayPedidoEnCursoException cuando no hay pedido en curso." );

        assertEquals( "Actualmente no hay un pedido en curso", ex.getMessage( ) );
    }

    @Test
    void testCerrarYGuardarPedidoExitoso( ) throws Exception
    {
        restaurante.iniciarPedido( "Carlos Ruiz", "Avenida 7 # 8-9" );
        Pedido pedido = restaurante.getPedidoEnCurso( );
        int idPedido = pedido.getIdPedido( );
        pedido.agregarProducto( new ProductoMenu( "corral", 14000 ) );

        restaurante.cerrarYGuardarPedido( );

        assertNull( restaurante.getPedidoEnCurso( ), "Luego de cerrar el pedido, pedidoEnCurso debe ser null." );
        assertEquals( 1, restaurante.getPedidos( ).size( ), "El pedido cerrado debe guardarse en la lista de pedidos." );
        assertEquals( idPedido, restaurante.getPedidos( ).get( 0 ).getIdPedido( ) );

        File archivoFactura = new File( "./facturas/factura_" + idPedido + ".txt" );
        assertTrue( archivoFactura.exists( ), "El archivo de factura debe haberse creado en ./facturas/." );
    }

    @Test
    void testCargarInformacionRestauranteExitoso( ) throws Exception
    {
        restaurante.cargarInformacionRestaurante( archivoIngredientesValido, archivoMenuValido, archivoCombosValido );

        assertEquals( 15, restaurante.getIngredientes( ).size( ), "Deben cargarse 15 ingredientes." );
        assertEquals( 22, restaurante.getMenuBase( ).size( ), "Deben cargarse 22 productos base en el menú." );
        assertEquals( 4, restaurante.getMenuCombos( ).size( ), "Deben cargarse 4 combos." );

        assertEquals( "lechuga", restaurante.getIngredientes( ).get( 0 ).getNombre( ) );
        assertEquals( "corral", restaurante.getMenuBase( ).get( 0 ).getNombre( ) );
        assertEquals( "combo corral", restaurante.getMenuCombos( ).get( 0 ).getNombre( ) );
    }

    @Test
    void testCargarIngredienteRepetidoLanzaExcepcion( ) throws Exception
    {
        String contenidoRepetido = "tomate;1000\ncebolla;1000\ntomate;1200\n";
        File archivoRepetido = crearArchivoTemporal( "ingredientes_repetidos.txt", contenidoRepetido );

        IngredienteRepetidoException ex = assertThrows( IngredienteRepetidoException.class, ( ) -> {
            restaurante.cargarInformacionRestaurante( archivoRepetido, archivoMenuValido, archivoCombosValido );
        }, "Debe lanzar IngredienteRepetidoException si un ingrediente se encuentra duplicado." );

        assertTrue( ex.getMessage( ).contains( "tomate" ) );
        assertTrue( ex.getMessage( ).contains( "repetido" ) );
    }

    @Test
    void testCargarProductoMenuRepetidoLanzaExcepcion( ) throws Exception
    {
        String contenidoRepetido = "corral;14000\npapas medianas;5500\ncorral;15000\n";
        File archivoRepetido = crearArchivoTemporal( "menu_repetido.txt", contenidoRepetido );

        ProductoRepetidoException ex = assertThrows( ProductoRepetidoException.class, ( ) -> {
            restaurante.cargarInformacionRestaurante( archivoIngredientesValido, archivoRepetido, archivoCombosValido );
        }, "Debe lanzar ProductoRepetidoException si un producto del menú se encuentra duplicado." );

        assertTrue( ex.getMessage( ).contains( "corral" ) );
        assertTrue( ex.getMessage( ).contains( "repetido" ) );
    }

    @Test
    void testCargarComboRepetidoLanzaExcepcion( ) throws Exception
    {
        String contenidoRepetido = "combo corral;10%;corral;papas medianas;gaseosa\n" +
                                   "combo corral;15%;corral;papas medianas;gaseosa\n";
        File archivoRepetido = crearArchivoTemporal( "combos_repetidos.txt", contenidoRepetido );

        ProductoRepetidoException ex = assertThrows( ProductoRepetidoException.class, ( ) -> {
            restaurante.cargarInformacionRestaurante( archivoIngredientesValido, archivoMenuValido, archivoRepetido );
        }, "Debe lanzar ProductoRepetidoException si un combo se encuentra duplicado." );

        assertTrue( ex.getMessage( ).contains( "combo corral" ) );
        assertTrue( ex.getMessage( ).contains( "repetido" ) );
    }

    @Test
    void testCargarComboProductoFaltanteLanzaExcepcion( ) throws Exception
    {
        String contenidoFaltante = "combo especial;10%;producto_inexistente_xyz;gaseosa\n";
        File archivoFaltante = crearArchivoTemporal( "combos_faltante.txt", contenidoFaltante );

        ProductoFaltanteException ex = assertThrows( ProductoFaltanteException.class, ( ) -> {
            restaurante.cargarInformacionRestaurante( archivoIngredientesValido, archivoMenuValido, archivoFaltante );
        }, "Debe lanzar ProductoFaltanteException si un combo contiene un producto no existente en el menú base." );

        assertTrue( ex.getMessage( ).contains( "producto_inexistente_xyz" ) );
        assertTrue( ex.getMessage( ).contains( "no aparece" ) );
    }

    @Test
    void testCargarInformacionArchivoInexistenteLanzaIOException( )
    {
        File archivoInexistente = new File( "archivo_que_no_existe_12345.txt" );

        assertThrows( IOException.class, ( ) -> {
            restaurante.cargarInformacionRestaurante( archivoInexistente, archivoMenuValido, archivoCombosValido );
        }, "Debe lanzar IOException si un archivo de entrada no existe." );
    }

    @Test
    void testCargarInformacionFormatoNumeroInvalido( ) throws Exception
    {
        String contenidoInvalido = "tomate;no_es_un_numero\n";
        File archivoInvalido = crearArchivoTemporal( "ingredientes_invalido.txt", contenidoInvalido );

        assertThrows( NumberFormatException.class, ( ) -> {
            restaurante.cargarInformacionRestaurante( archivoInvalido, archivoMenuValido, archivoCombosValido );
        }, "Debe lanzar NumberFormatException si el costo no es numérico." );
    }
}
