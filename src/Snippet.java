import java.io.File;
import java.io.FileNotFoundException;
import org.coode.xml.XMLWriterPreferences;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;

import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;

import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;

import org.semanticweb.owlapi.util.DefaultPrefixManager;

public class Snippet {
    private static Object ProtegeOWL;
  
public static void main(String[] args) throws FileNotFoundException, OWLOntologyStorageException, OWLOntologyCreationException {

    IRI ontologyIRI = IRI.create("http://www.semanticweb.org/bisma/ontologies/2016/2/university.owl");
OWLOntologyManager man = OWLManager.createOWLOntologyManager();
OWLOntology ontology = man.loadOntologyFromOntologyDocument(new File("university.owl"));
OWLDataFactory factory = man.getOWLDataFactory();
PrefixManager pm = new DefaultPrefixManager(ontologyIRI+"#");
//insert without adding type:
OWLClass student = factory.getOWLClass(":Student",pm);
OWLDeclarationAxiom declaration = factory.getOWLDeclarationAxiom(student);
man.addAxiom(ontology, declaration);man.removeAxiom(ontology, declaration);
OWLClass person = factory.getOWLClass(":Person", pm);
OWLNamedIndividual mary = factory.getOWLNamedIndividual(":Mary", pm);
//insert with adding type:
OWLClassAssertionAxiom classAssertion = factory.getOWLClassAssertionAxiom(person, mary);
man.addAxiom(ontology, classAssertion);
man.removeAxiom(ontology, classAssertion);
//to add object property assertion
    OWLIndividual john = factory.getOWLNamedIndividual(IRI
            .create(ontologyIRI + "#John"));
    
    OWLIndividual susan = factory.getOWLNamedIndividual(IRI
            .create(ontologyIRI + "#Susan"));
    OWLIndividual bill = factory.getOWLNamedIndividual(IRI
            .create(ontologyIRI + "#Bill"));

    OWLObjectProperty hasWife = factory.getOWLObjectProperty(IRI
            .create(ontologyIRI + "#hasWife"));

    OWLObjectPropertyAssertionAxiom axiom1 = factory
            .getOWLObjectPropertyAssertionAxiom(hasWife, john, susan);
      AddAxiom addAxiom1 = new AddAxiom(ontology, axiom1);
    man.applyChange(addAxiom1);
   man.removeAxiom(ontology, axiom1);
   
    OWLDataProperty hasAge = factory.getOWLDataProperty(IRI.create(ontologyIRI + "#hasAe"));
            // We create a data property assertion 
            OWLAxiom axiom4 = factory.getOWLDataPropertyAssertionAxiom(hasAge, john, 33);
            //man.applyChange(new AddAxiom(ontology, axiom4));
man.removeAxiom(ontology, axiom4);
  

    OWLClass men = factory.getOWLClass(IRI.create(ontologyIRI + "#Man"));
            OWLClass woman = factory.getOWLClass(IRI.create(ontologyIRI + "#Woman"));
            
            man.addAxiom(ontology, factory.getOWLSubClassOfAxiom(men, person));
            man.addAxiom(ontology, factory.getOWLSubClassOfAxiom(woman, person));
            XMLWriterPreferences.getInstance().setUseNamespaceEntities(true);
            man.removeAxiom(ontology, factory.getOWLSubClassOfAxiom(men, person));
man.saveOntology(ontology, new OWLXMLOntologyFormat());
   
}}