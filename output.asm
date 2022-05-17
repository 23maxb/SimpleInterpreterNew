	# Program Generated from AST lab by Max Blennemann from output
	.data
varx:
	.word 0
varcount:
	.word 0
vary:
	.word 0
	.text
	.globl main
main:
	li $t0, 2
	sw $t0 varx#pop off stack
	lw $t0, varx#push on stack
	move $t1, $t0
	li $t0, 1
	move $t2, $t0
	add $t0, $t1, $t2 #adding values
	sw $t0 vary#pop off stack
	lw $t0, varx#push on stack
	move $t1, $t0
	lw $t0, vary#push on stack
	move $t2, $t0
	add $t0, $t1, $t2 #adding values
	sw $t0 varx#pop off stack
	li $v0, 1
	lw $t0, varx#push on stack
	move $t1, $t0
	lw $t0, vary#push on stack
	move $t2, $t0
	mult $t1, $t2
	mflo $t0
	move $a0, $t0
	syscall
	li $a0 '\n'
	li $v0 11
	syscall
	lw $t0, varx#push on stack
	move $t1, $t0
	lw $t0, vary#push on stack
	move $t2, $t0
	li $t0, 1
	bgt $t1, $t2, label0
	li $t0, 0
label0:
	beq $t0, $0, label1
	li $v0, 1
	lw $t0, varx#push on stack
	move $a0, $t0
	syscall
	li $a0 '\n'
	li $v0 11
	syscall
	li $v0, 1
	lw $t0, vary#push on stack
	move $a0, $t0
	syscall
	li $a0 '\n'
	li $v0 11
	syscall
label1:
	li $t0, 1
	beq $t0, $0, label2
	li $t0, 14
	move $t1, $t0
	li $t0, 14
	move $t2, $t0
	li $t0, 1
	bne $t1, $t2, label3
	li $t0, 0
label3:
	beq $t0, $0, label4
	li $v0, 1
	li $t0, 3
	move $a0, $t0
	syscall
	li $a0 '\n'
	li $v0 11
	syscall
label4:
	li $t0, 14
	move $t1, $t0
	li $t0, 14
	move $t2, $t0
	li $t0, 1
	blt $t1, $t2, label5
	beq $t1, $t2, label5
	li $t0, 0
label5:
	beq $t0, $0, label6
	li $v0, 1
	li $t0, 4
	move $a0, $t0
	syscall
	li $a0 '\n'
	li $v0 11
	syscall
label6:
label2:
	li $t0, 15
	move $t1, $t0
	li $t0, 14
	move $t2, $t0
	li $t0, 1
	bgt $t1, $t2, label7
	li $t0, 0
label7:
	beq $t0, $0, label8
	li $v0, 1
	li $t0, 5
	move $a0, $t0
	syscall
	li $a0 '\n'
	li $v0 11
	syscall
label8:
	li $t0, 1
	sw $t0 varcount#pop off stack
label10:
	lw $t0, varcount#push on stack
	move $t1, $t0
	li $t0, 15
	move $t2, $t0
	li $t0, 1
	blt $t1, $t2, label11
	beq $t1, $t2, label11
	li $t0, 0
label11:
	beq $t0, $0, label9
	li $v0, 1
	lw $t0, varcount#push on stack
	move $a0, $t0
	syscall
	li $a0 '\n'
	li $v0 11
	syscall
	lw $t0, varcount#push on stack
	move $t1, $t0
	li $t0, 1
	move $t2, $t0
	add $t0, $t1, $t2 #adding values
	sw $t0 varcount#pop off stack
	j label10
label9:
	li $v0, 10
	syscall
