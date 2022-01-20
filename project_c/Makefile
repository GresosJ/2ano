TARGET_EXEC ?= program

CC=gcc

IDIR ?= ./include ./include/model ./include/controller ./include/modules ./include/repositories ./include/view /usr/include/glib-2.0 /usr/lib/x86_64-linux-gnu/glib-2.0/include
ODIR ?= ./obj
SDIR ?= ./src
LDIR ?= ./lib

PKG = -lglib-2.0
CFLAGS=-std=c99 -D_GNU_SOURCE -Wall -O3 -g $(PKG)

SRCS := $(shell find $(SDIR) -name "*.c")
HDRS := $(shell find $($(IDIR) $<)  -name *.h)
OBJS := $(patsubst %.c, $(ODIR)/%.o, $(notdir $(SRCS)))
DEPS := $(OBJS:.o=.d)

INC_DIRS := $(shell find $(INC_DIR) -type d)
INC_FLAGS := $(patsubst %, -I%, $(IDIR))

CREATE_OBJ_FOLDER := $(shell mkdir -p $(ODIR))

$(TARGET_EXEC): $(OBJS)
	$(CC) $(CFLAGS) $(OBJS) -o $@

# c source
$(ODIR)/main.o: $(SDIR)/main.c
	$(CC) $(CFLAGS) $(INC_FLAGS) -c $< -o $@

$(ODIR)/business.o: $(SDIR)/model/business.c $(ODIR)/hash_table.o
	$(CC) $(CFLAGS) $(INC_FLAGS) -c $< -o $@

$(ODIR)/db.o: $(SDIR)/model/db.c $(ODIR)/hash_table.o $(ODIR)/business.o $(ODIR)/user.o $(ODIR)/review.o
	$(CC) $(CFLAGS) $(INC_FLAGS) -c $< -o $@

$(ODIR)/review.o: $(SDIR)/model/review.c $(ODIR)/hash_table.o
	$(CC) $(CFLAGS) $(INC_FLAGS) -c $< -o $@

$(ODIR)/sgr.o: $(SDIR)/controller/sgr.c $(ODIR)/db.o $(ODIR)/user.o $(ODIR)/review.o $(ODIR)/business.o $(ODIR)/hash_table.o $(ODIR)/businessRepository.o
	$(CC) $(CFLAGS) $(INC_FLAGS) -c $< -o $@

$(ODIR)/user.o: $(SDIR)/model/user.c $(ODIR)/hash_table.o
	$(CC) $(CFLAGS) $(INC_FLAGS) -c $< -o $@

$(ODIR)/interface.o: $(SDIR)/model/interface.c
	$(CC) $(CFLAGS) $(INC_FLAGS) -c $< -o $@

$(ODIR)/hash_table.o: $(SDIR)/modules/hash_table.c
	$(CC) $(CFLAGS) $(INC_FLAGS) -c $< -o $@

$(ODIR)/table.o: $(SDIR)/modules/table.c
	$(CC) $(CFLAGS) $(INC_FLAGS) -c $< -o $@

$(ODIR)/businessRepository.o: $(SDIR)/repositories/businessRepository.c $(ODIR)/business.o $(ODIR)/hash_table.o $(ODIR)/db.o
	$(CC) $(CFLAGS) $(INC_FLAGS) -c $< -o $@

$(ODIR)/envRepository.o: $(SDIR)/repositories/envRepository.c $(ODIR)/hash_table.o
	$(CC) $(CFLAGS) $(INC_FLAGS) -c $< -o $@

$(ODIR)/reviewRepository.o: $(SDIR)/repositories/reviewRepository.c $(ODIR)/userRepository.o
	$(CC) $(CFLAGS) $(INC_FLAGS) -c $< -o $@

$(ODIR)/userRepository.o: $(SDIR)/repositories/userRepository.c
	$(CC) $(CFLAGS) $(INC_FLAGS) -c $< -o $@

$(ODIR)/table_view.o: $(SDIR)/view/table_view.c
	$(CC) $(CFLAGS) $(INC_FLAGS) -c $< -o $@

$(ODIR)/interface_view.o: $(SDIR)/view/interface_view.c
	$(CC) $(CFLAGS) $(INC_FLAGS) -c $< -o $@


# $(ODIR)/%.o: $(SRCS)
# 	$(CC) $(CFLAGS) $(INC_FLAGS) -c $< -o $@

.PHONY: clean

clean:
	$(RM) -r $(ODIR) \\
	$(RM) $(TARGET_EXEC)

-include $(DEPS)

MKDIR_P ?= mkdir -p

