package com.adias.fap.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.adias.fap.service.InvoiceLineService;
import com.adias.fap.web.rest.errors.BadRequestAlertException;
import com.adias.fap.web.rest.util.HeaderUtil;
import com.adias.fap.service.dto.InvoiceLineDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing InvoiceLine.
 */
@RestController
@RequestMapping("/api")
public class InvoiceLineResource {

    private final Logger log = LoggerFactory.getLogger(InvoiceLineResource.class);

    private static final String ENTITY_NAME = "invoiceLine";

    private final InvoiceLineService invoiceLineService;

    public InvoiceLineResource(InvoiceLineService invoiceLineService) {
        this.invoiceLineService = invoiceLineService;
    }

    /**
     * POST  /invoice-lines : Create a new invoiceLine.
     *
     * @param invoiceLineDTO the invoiceLineDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new invoiceLineDTO, or with status 400 (Bad Request) if the invoiceLine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/invoice-lines")
    @Timed
    public ResponseEntity<InvoiceLineDTO> createInvoiceLine(@RequestBody InvoiceLineDTO invoiceLineDTO) throws URISyntaxException {
        log.debug("REST request to save InvoiceLine : {}", invoiceLineDTO);
        if (invoiceLineDTO.getId() != null) {
            throw new BadRequestAlertException("A new invoiceLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvoiceLineDTO result = invoiceLineService.save(invoiceLineDTO);
        return ResponseEntity.created(new URI("/api/invoice-lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /invoice-lines : Updates an existing invoiceLine.
     *
     * @param invoiceLineDTO the invoiceLineDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated invoiceLineDTO,
     * or with status 400 (Bad Request) if the invoiceLineDTO is not valid,
     * or with status 500 (Internal Server Error) if the invoiceLineDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/invoice-lines")
    @Timed
    public ResponseEntity<InvoiceLineDTO> updateInvoiceLine(@RequestBody InvoiceLineDTO invoiceLineDTO) throws URISyntaxException {
        log.debug("REST request to update InvoiceLine : {}", invoiceLineDTO);
        if (invoiceLineDTO.getId() == null) {
            return createInvoiceLine(invoiceLineDTO);
        }
        InvoiceLineDTO result = invoiceLineService.save(invoiceLineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, invoiceLineDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /invoice-lines : get all the invoiceLines.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of invoiceLines in body
     */
    @GetMapping("/invoice-lines")
    @Timed
    public List<InvoiceLineDTO> getAllInvoiceLines() {
        log.debug("REST request to get all InvoiceLines");
        return invoiceLineService.findAll();
        }

    /**
     * GET  /invoice-lines/:id : get the "id" invoiceLine.
     *
     * @param id the id of the invoiceLineDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the invoiceLineDTO, or with status 404 (Not Found)
     */
    @GetMapping("/invoice-lines/{id}")
    @Timed
    public ResponseEntity<InvoiceLineDTO> getInvoiceLine(@PathVariable Long id) {
        log.debug("REST request to get InvoiceLine : {}", id);
        InvoiceLineDTO invoiceLineDTO = invoiceLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(invoiceLineDTO));
    }

    /**
     * DELETE  /invoice-lines/:id : delete the "id" invoiceLine.
     *
     * @param id the id of the invoiceLineDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/invoice-lines/{id}")
    @Timed
    public ResponseEntity<Void> deleteInvoiceLine(@PathVariable Long id) {
        log.debug("REST request to delete InvoiceLine : {}", id);
        invoiceLineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
