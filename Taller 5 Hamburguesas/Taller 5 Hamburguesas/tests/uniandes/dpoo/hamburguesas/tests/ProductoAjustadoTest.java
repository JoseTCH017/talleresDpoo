package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoAjustadoTest
{
    private ProductoMenu base1;
    private ProductoAjustado ajustado1;
    private Ingrediente queso;
    private Ingrediente tocineta;
    private Ingrediente cebolla;

    @BeforeEach
    void setUp( ) throws Exception
    {
        base1 = new ProductoMenu( "corral", 14000 );
        ajustado1 = new ProductoAjustado( base1 );
        queso = new Ingrediente( "queso mozzarella", 2500 );
        tocineta = new Ingrediente( "tocineta express", 2500 );
        cebolla = new Ingrediente( "cebolla", 1000 );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }

    @Test
    void testGetNombre( )
    {
        assertEquals( "corral", ajustado1.getNombre( ), "El nombre del producto ajustado debe coincidir con el del producto base." );
    }

    @Test
    void testGetPrecioSinAjustes( )
    {
        assertEquals( 14000, ajustado1.getPrecio( ), "El precio de un producto ajustado sin agregados debe ser el precio base." );
    }

    @Test
    void testGetPrecioConIngredientesAgregados( )
    {
        ajustado1.agregarIngrediente( queso );
        assertEquals( 16500, ajustado1.getPrecio( ), "El precio debe incluir el costo del ingrediente agregado." );

        ajustado1.agregarIngrediente( tocineta );
        assertEquals( 19000, ajustado1.getPrecio( ), "El precio debe incluir la suma de todos los ingredientes agregados." );
    }

    @Test
    void testGetPrecioConIngredientesEliminados( )
    {
        ajustado1.eliminarIngrediente( cebolla );
        assertEquals( 14000, ajustado1.getPrecio( ), "Eliminar ingredientes no debe modificar el precio del producto." );
    }

    @Test
    void testGetPrecioConAgregadosYEliminados( )
    {
        ajustado1.agregarIngrediente( queso );
        ajustado1.eliminarIngrediente( cebolla );
        assertEquals( 16500, ajustado1.getPrecio( ), "El precio solo debe aumentar por los ingredientes agregados." );
    }

    @Test
    void testGenerarTextoFacturaSinAjustes( )
    {
        String factura = ajustado1.generarTextoFactura( );
        assertTrue( factura.contains( "corral" ), "La factura debe incluir el nombre del producto base." );
        assertTrue( factura.contains( "14000" ), "La factura debe incluir el precio del producto base." );
    }

    @Test
    void testGenerarTextoFacturaConAgregadosYEliminados( )
    {
        ajustado1.agregarIngrediente( queso );
        ajustado1.eliminarIngrediente( cebolla );

        String factura = ajustado1.generarTextoFactura( );
        assertTrue( factura.contains( "corral" ), "La factura debe incluir el producto base." );
        assertTrue( factura.contains( "+queso mozzarella" ), "La factura debe indicar los ingredientes agregados precedidos de +." );
        assertTrue( factura.contains( "2500" ), "La factura debe incluir el costo del ingrediente adicional." );
        assertTrue( factura.contains( "-cebolla" ), "La factura debe indicar los ingredientes eliminados precedidos de -." );
        assertTrue( factura.contains( "16500" ), "La factura debe reflejar el precio total ajustado." );
    }

    @Test
    void testGetAgregadosYEliminados( )
    {
        assertEquals( 0, ajustado1.getAgregados( ).size( ), "Inicialmente la lista de agregados debe estar vacía." );
        assertEquals( 0, ajustado1.getEliminados( ).size( ), "Inicialmente la lista de eliminados debe estar vacía." );

        ajustado1.getAgregados( ).add( queso );
        ajustado1.getEliminados( ).add( cebolla );

        assertEquals( 1, ajustado1.getAgregados( ).size( ), "Debe haber 1 ingrediente en la lista de agregados." );
        assertEquals( 1, ajustado1.getEliminados( ).size( ), "Debe haber 1 ingrediente en la lista de eliminados." );
        assertEquals( queso, ajustado1.getAgregados( ).get( 0 ) );
        assertEquals( cebolla, ajustado1.getEliminados( ).get( 0 ) );
    }
}
