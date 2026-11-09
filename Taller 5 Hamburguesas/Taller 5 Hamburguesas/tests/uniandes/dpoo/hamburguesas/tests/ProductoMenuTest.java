package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoMenuTest
{
    private ProductoMenu producto1;
    private ProductoMenu producto2;

    @BeforeEach
    void setUp( ) throws Exception
    {
        producto1 = new ProductoMenu( "corral", 14000 );
        producto2 = new ProductoMenu( "papas medianas", 5500 );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }

    @Test
    void testGetNombre( )
    {
        assertEquals( "corral", producto1.getNombre( ), "El nombre del producto no es el esperado." );
        assertEquals( "papas medianas", producto2.getNombre( ), "El nombre del producto no es el esperado." );
    }

    @Test
    void testGetPrecio( )
    {
        assertEquals( 14000, producto1.getPrecio( ), "El precio del producto no es el esperado." );
        assertEquals( 5500, producto2.getPrecio( ), "El precio del producto no es el esperado." );
    }

    @Test
    void testGenerarTextoFactura( )
    {
        String esperado1 = "corral\n            14000\n";
        assertEquals( esperado1, producto1.generarTextoFactura( ), "El texto de la factura no es el esperado." );

        String esperado2 = "papas medianas\n            5500\n";
        assertEquals( esperado2, producto2.generarTextoFactura( ), "El texto de la factura no es el esperado." );
    }
}
