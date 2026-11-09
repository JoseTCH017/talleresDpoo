package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class PedidoTest
{
    private Pedido pedido1;
    private File archivoFacturaPrueba;

    @BeforeEach
    void setUp( ) throws Exception
    {
        pedido1 = new Pedido( "Carlos Gomez", "Calle 123 # 45-67" );
        archivoFacturaPrueba = new File( "factura_test_temp.txt" );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
        if( archivoFacturaPrueba != null && archivoFacturaPrueba.exists( ) )
        {
            archivoFacturaPrueba.delete( );
        }
    }

    @Test
    void testGetIdPedido( )
    {
        int id1 = pedido1.getIdPedido( );
        Pedido pedido2 = new Pedido( "Ana Lopez", "Carrera 10 # 20-30" );
        assertEquals( id1 + 1, pedido2.getIdPedido( ), "El identificador del pedido debe incrementarse con cada nuevo pedido." );
    }

    @Test
    void testGetNombreCliente( )
    {
        assertEquals( "Carlos Gomez", pedido1.getNombreCliente( ), "El nombre del cliente no coincide con el esperado." );
    }

    @Test
    void testPedidoVacio( )
    {
        assertEquals( 0, pedido1.getPrecioTotalPedido( ), "Un pedido vacío debe tener precio total 0." );

        String factura = pedido1.generarTextoFactura( );
        assertTrue( factura.contains( "Cliente: Carlos Gomez\n" ), "La factura debe incluir el nombre del cliente." );
        assertTrue( factura.contains( "Dirección: Calle 123 # 45-67\n" ), "La factura debe incluir la dirección del cliente." );
        assertTrue( factura.contains( "Precio Neto:  0\n" ), "El precio neto de un pedido vacío debe ser 0." );
        assertTrue( factura.contains( "IVA:          0\n" ), "El IVA de un pedido vacío debe ser 0." );
        assertTrue( factura.contains( "Precio Total: 0\n" ), "El precio total de un pedido vacío debe ser 0." );
    }

    @Test
    void testAgregarProductoYPrecios( )
    {
        ProductoMenu hamburguesa = new ProductoMenu( "corral", 14000 );
        pedido1.agregarProducto( hamburguesa );

        // Neto: 14000, IVA: (int)(14000 * 0.19) = 2660, Total: 16660
        int esperadoNeto = 14000;
        int esperadoIVA = ( int ) ( esperadoNeto * 0.19 );
        int esperadoTotal = esperadoNeto + esperadoIVA;

        assertEquals( esperadoTotal, pedido1.getPrecioTotalPedido( ), "El precio total del pedido no es el esperado." );

        ProductoMenu papas = new ProductoMenu( "papas medianas", 5500 );
        pedido1.agregarProducto( papas );

        esperadoNeto = 14000 + 5500; // 19500
        esperadoIVA = ( int ) ( esperadoNeto * 0.19 ); // 3705
        esperadoTotal = esperadoNeto + esperadoIVA; // 23205

        assertEquals( esperadoTotal, pedido1.getPrecioTotalPedido( ), "El precio total del pedido con dos productos no es el esperado." );
    }

    @Test
    void testGenerarTextoFacturaConMultiplesProductos( )
    {
        ProductoMenu hamburguesa = new ProductoMenu( "corral", 14000 );
        ProductoAjustado ajustado = new ProductoAjustado( hamburguesa );
        ajustado.agregarIngrediente( new Ingrediente( "queso mozzarella", 2500 ) );

        ArrayList<ProductoMenu> itemsCombo = new ArrayList<>( );
        itemsCombo.add( hamburguesa );
        itemsCombo.add( new ProductoMenu( "gaseosa", 5000 ) );
        Combo combo = new Combo( "combo corral", 0.10, itemsCombo );

        pedido1.agregarProducto( hamburguesa );
        pedido1.agregarProducto( ajustado );
        pedido1.agregarProducto( combo );

        int neto = hamburguesa.getPrecio( ) + ajustado.getPrecio( ) + combo.getPrecio( );
        int iva = ( int ) ( neto * 0.19 );
        int total = neto + iva;

        String factura = pedido1.generarTextoFactura( );

        assertTrue( factura.contains( "Cliente: Carlos Gomez\n" ) );
        assertTrue( factura.contains( "Dirección: Calle 123 # 45-67\n" ) );
        assertTrue( factura.contains( hamburguesa.generarTextoFactura( ) ) );
        assertTrue( factura.contains( ajustado.generarTextoFactura( ) ) );
        assertTrue( factura.contains( combo.generarTextoFactura( ) ) );
        assertTrue( factura.contains( "Precio Neto:  " + neto + "\n" ) );
        assertTrue( factura.contains( "IVA:          " + iva + "\n" ) );
        assertTrue( factura.contains( "Precio Total: " + total + "\n" ) );
    }

    @Test
    void testGuardarFacturaExitoso( ) throws Exception
    {
        ProductoMenu producto = new ProductoMenu( "corral", 14000 );
        pedido1.agregarProducto( producto );

        pedido1.guardarFactura( archivoFacturaPrueba );
        assertTrue( archivoFacturaPrueba.exists( ), "El archivo de factura debió ser creado." );

        StringBuilder contenido = new StringBuilder( );
        BufferedReader reader = new BufferedReader( new FileReader( archivoFacturaPrueba ) );
        String linea = reader.readLine( );
        while( linea != null )
        {
            contenido.append( linea ).append( "\n" );
            linea = reader.readLine( );
        }
        reader.close( );

        assertEquals( pedido1.generarTextoFactura( ), contenido.toString( ), "El contenido guardado en el archivo no coincide con el texto de la factura." );
    }

    @Test
    void testGuardarFacturaLanzaFileNotFoundException( )
    {
        // Ruta inválida en una carpeta que no existe
        File archivoInvalido = new File( "Z:/carpeta_inexistente_12345/factura.txt" );

        assertThrows( FileNotFoundException.class, ( ) -> {
            pedido1.guardarFactura( archivoInvalido );
        }, "Debe lanzar FileNotFoundException si la ruta o archivo no es válido." );
    }
}
